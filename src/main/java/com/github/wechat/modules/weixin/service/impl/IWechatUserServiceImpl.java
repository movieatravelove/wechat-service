package com.github.wechat.modules.weixin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.wechat.entity.Result;
import com.github.wechat.exception.ServiceException;
import com.github.wechat.modules.weixin.RedisDao.AccessTokenRedisDao;
import com.github.wechat.modules.weixin.entity.AccessTokenDO;
import com.github.wechat.modules.weixin.entity.WechatUserDTO;
import com.github.wechat.modules.weixin.entity.WechatUserVO;
import com.github.wechat.modules.weixin.mapper.WechatUserMapper;
import com.github.wechat.modules.weixin.service.IWechatUserService;
import com.github.wechat.modules.weixin.utils.WeixinUtil;
import com.github.wechat.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: zhang
 * @Date: 2019/12/12/012 11:10
 * @Description:
 */
@Slf4j
@Service
public class IWechatUserServiceImpl implements IWechatUserService {


    @Value("${app.WX_APPID}")
    private String WX_APPID;
    @Value("${app.WX_APPSECRET}")
    private String WX_APPSECRET;
    @Value("${app.WX_OAUTH_ACCESS_TOKEN_URL}")
    private String WX_OAUTH_ACCESS_TOKEN_URL;
    @Value("${app.WX_OAUTH_REFRESH_TOKEN_URL}")
    private String WX_OAUTH_REFRESH_TOKEN_URL;
    @Value("${app.WX_USERINFO_URL}")
    private String WX_USERINFO_URL;
    @Value("${app.WX_CUSTOM_MENU}")
    private String WX_CUSTOM_MENU_URL;
    @Value("${app.WX_GET_MENU}")
    private String WX_GET_MENU_URL;
    @Value("${app.WX_CREATE_QRCODE_TICKET}")
    private String WX_CREATE_QRCODE_TICKET_URL;
    @Value("${app.WX_TICKET_TO_QRCODE}")
    private String WX_TICKET_TO_QRCODE_URL;
    @Value("${app.WX_GETTICKET}")
    private String WX_GETTICKET_URL;
    @Autowired
    private WeixinUtil weixinUtil;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private WechatUserMapper wechatUserMapper;
    @Autowired
    private AccessTokenRedisDao accessTokenRedisDao;
    @Autowired
    private UserQrcodeService userQrcodeService;

    @Value("classpath:static/data/menu.json")
    private Resource MENU_JSON;


    /**
     * 通过code 换取网页授权access_token
     * code只能使用一次，5分钟未被使用自动过期。
     *
     * @param code
     * @return
     */
    @Override
    public Result getOauthAccessToken(String code) {
        // 请求网页授权获取ACCESS_TOKEN
        String getAccessTokenUrl = WX_OAUTH_ACCESS_TOKEN_URL + "?appid=" + WX_APPID +
                "&secret=" + WX_APPSECRET +
                "&code=" + code +
                "&grant_type=authorization_code";
        String authResult = restTemplate.getForObject(getAccessTokenUrl, String.class);
        JSONObject json = JSONObject.parseObject(authResult);
        AccessTokenDO authAccessToken = getAuthAccessToken(json);
        String openId = authAccessToken.getOpenid();
        String accessToken = authAccessToken.getAccessToken();
        // 根据授权token获取用户信息
        String authUserInfo = getAuthUserInfo(accessToken, openId);
        System.out.println("userInfo:" + authUserInfo);
        System.out.println("=====================");
        // 保存/更新用户信息
        WechatUserDTO wechatUser = saveOrUpdateWechatUseer(authUserInfo);
        // 返回信息
        WechatUserVO result = getResult(wechatUser);
        System.out.println("result:" + result);
        return new Result().success(result);
    }


    /**
     * 去缓存中获取openId
     * 如果openId不存在，则证明code为首次使用，可以根据传过来的code获取相应的access_token和openId。 -> getOauthAccessToken
     * 如果存在，则直接使用获取到的openId去获取用户的一系列信息 -> getUserInfo
     *
     * @param openId
     * @return
     */
    public Result getUserInfo(String openId) {
        log.info("openId:" + openId);
        // 使用openId获取用户信息 返回
        WechatUserDTO wechatUserDTO = wechatUserMapper.selectWechatUserByOpenId(openId);
        WechatUserVO wechatUser = getResult(wechatUserDTO);
        return new Result().success(wechatUser);
    }


    @Override
    public boolean createCustomMenu(String json) {
        String menu = json;
        String data = "";
        try {
            if (StringUtils.isEmpty(json)) {
                menu = IOUtils.toString(MENU_JSON.getInputStream(), Charset.forName("UTF-8"));
            }
            menu = new String(menu.getBytes(), "UTF-8");
            Object parse = JSON.parse(menu);
            System.out.println("menu:" + parse);
            String accessToken = getAccessToken().getAccessToken();
            String url = WX_CUSTOM_MENU_URL.replace("ACCESS_TOKEN", accessToken);
            data = HttpClientUtil.post(url, menu);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = JSONObject.parseObject(data);
        if (jsonObject != null && jsonObject.getIntValue("errcode") == 0) {
            log.info("菜单创建成功！");
            return true;
        } else {
            log.info("菜单创建失败，错误码：" + jsonObject.getIntValue("errcode"));
            return false;
        }
    }

    @Override
    public JSONObject getMenu() {
        AccessTokenDO accessToken = getAccessToken();
        String url = WX_GET_MENU_URL.replace("ACCESS_TOKEN", accessToken.getAccessToken());
        String forObject = restTemplate.getForObject(url, String.class);
        return JSONObject.parseObject(forObject);
    }


    @Override
    public String createQrCode(String param) {
        AccessTokenDO accessToken = getAccessToken();
        String url = WX_CREATE_QRCODE_TICKET_URL.replace("ACCESS_TOKEN", accessToken.getAccessToken());
        Map<String, String> intMap = new HashMap<>();
        intMap.put("scene_str", param);
        Map<String, Map<String, String>> mapMap = new HashMap<>();
        mapMap.put("scene", intMap);
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("action_name", "QR_LIMIT_STR_SCENE");
        paramsMap.put("action_info", mapMap);
        JSONObject object = restTemplate.postForObject(url, JSONObject.toJSONString(paramsMap), JSONObject.class);
        String ticket = object.getString("ticket");
        String img = WX_TICKET_TO_QRCODE_URL.replace("TICKET", ticket);
        return img;
    }


    private AccessTokenDO getAuthAccessToken(JSONObject json) {
        throwError(json);
        AccessTokenDO accessTokenDO = new AccessTokenDO();
        String accessToken = json.getString("access_token");
        String rsOpenid = json.getString("openid");
        accessTokenDO.setOpenid(rsOpenid);
        accessTokenDO.setAccessToken(accessToken);
        accessTokenDO.setExpiresIn(json.getString("expires_in"));
        accessTokenDO.setRefreshToken(json.getString("refresh_token"));
        accessTokenDO.setCreateTime(new Date());
        return accessTokenDO;
    }


    @Override
    public AccessTokenDO getAccessToken() {
        AccessTokenDO accessToken;
        try {
            accessToken = accessTokenRedisDao.getAccessToken();
            long exTime = accessToken.getCreateTime().getTime() + Long.parseLong(accessToken.getExpiresIn()) * 1000;
            if (accessToken == null || exTime < System.currentTimeMillis()) {
                // 第一次使用或者过期 重新生成
                accessToken = weixinUtil.getAccessTokenFromWX(WX_APPID, WX_APPSECRET);
                accessTokenRedisDao.addAccessToken(accessToken);
            }
        } catch (Exception e) {
            accessToken = weixinUtil.getAccessTokenFromWX(WX_APPID, WX_APPSECRET);
        }
        return accessToken;
    }

    /**
     * 根据授权token获取用户信息
     *
     * @param accessToken
     * @param openid
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public String getAuthUserInfo(String accessToken, String openid) {
        String user = null;
        String getUserInfoUrl = WX_USERINFO_URL + "?access_token=" + accessToken +
                "&openid=" + openid +
                "&lang=zh_CN";
        try {
            user = restTemplate.getForObject(getUserInfoUrl, String.class);
            user = new String(user.getBytes("ISO-8859-1"), "UTF-8");
        } catch (Exception e) {
            log.error("获取用户信息失败");
        }
        return user;
    }


    private WechatUserVO getResult(WechatUserDTO wechatUserDTO) {
        WechatUserVO wechatUser = new WechatUserVO();
        BeanUtils.copyProperties(wechatUserDTO, wechatUser);
        Long userId = wechatUserDTO.getId();
        Map<String, Object> payload = new HashMap<>();
        payload.put("userId", userId);
        Date expiresDate = TokenUtil.getExpiresDate(7);
        String token = TokenUtil.buildToken("wxToken", payload, expiresDate);
        wechatUser.setToken(token);
        wechatUser.setExpiresDate(expiresDate.getTime());
        wechatUser.setUserId(userId);
        String qrCode = createQrCode("proxyId_" + userId);
        // 合成二维码 + 头像
        String qrCodeImg = userQrcodeService.composeImage(String.valueOf(userId), qrCode,
                wechatUserDTO.getHeadImgUrl());
        wechatUser.setQrCodeImg(qrCodeImg);
        wechatUser.setNonceStr(getSign().getNonceStr());
        wechatUser.setTimestamp(getSign().getTimestamp());
        wechatUser.setSignature(getSign().getSignature());
        return wechatUser;
    }


    /**
     * 保存/更新微信用户信息
     *
     * @param user
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public WechatUserDTO saveOrUpdateWechatUseer(String user) {
        JSONObject userJson = JSONObject.parseObject(user);
        throwError(userJson);
        String openid = userJson.getString("openid");
        WechatUserDTO wechatUserDTO = new WechatUserDTO();
        wechatUserDTO.setOpenid(openid);
        wechatUserDTO.setNickname(EmojiStringUtils.replaceEmoji(userJson.getString("nickname")));
        wechatUserDTO.setSex(userJson.getInteger("sex"));
        wechatUserDTO.setHeadImgUrl(userJson.getString("headimgurl"));
        wechatUserDTO.setCountry(userJson.getString("country"));
        wechatUserDTO.setProvince(userJson.getString("province"));
        wechatUserDTO.setCity(userJson.getString("city"));
        //与开放平台共用的唯一标识，只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
        if (userJson.containsKey("unionid")) {
            String unionid = userJson.getString("unionid");
            wechatUserDTO.setWechatNo(unionid);
        }
        // 查询是否存在该用户
        WechatUserDTO wechatUser = wechatUserMapper.selectWechatUserByOpenId(openid);
        if (wechatUser == null) {
            // 插入返回id
            wechatUserMapper.insertWechatUser(wechatUserDTO);
        } else {
            wechatUserDTO.setId(wechatUser.getId());
            wechatUserMapper.updateWechatUser(wechatUserDTO);
        }
        return wechatUserDTO;
    }


    private void throwError(JSONObject json) {
        if (json.containsKey("errcode")) {
            throw new ServiceException(json.get("errmsg").toString(), json.getInteger("errcode"));
        }
    }


    public WechatUserVO getSign() {
        WechatUserVO wechatUser = new WechatUserVO();
        AccessTokenDO accessToken = getAccessToken();
        String requestUrl = WX_GETTICKET_URL.replace("ACCESS_TOKEN", accessToken.getAccessToken());
        String data = restTemplate.getForObject(requestUrl, String.class);
        JSONObject json = JSONObject.parseObject(data);
        if (json != null && json.getInteger("errcode") == 0) {
            // 1.获取ticket
            String ticket = json.getString("ticket");
            // 2.按照ASCII 码从小到大排序
            // 3.拼成字符串string1 即(key1=value1&key2=value2…)
            // 10位时间戳，java默认生成13位
            long timeStamp = Long.parseLong(DateUtil.get13Timestamp());
            String noncestr = RandomUtils.getRandom(18);
            String url = "localhost:8081/index.html";
            String string1 = "jsapi_ticket=" + ticket + "&noncestr=" + noncestr + "&timestamp="
                    + timeStamp + "&url=" + url;
            // 4.对string1作sha1加密signature=sha1(string1)
            String signature = sha1(string1);
            wechatUser.setNonceStr(noncestr);
            wechatUser.setTimestamp(timeStamp);
            wechatUser.setSignature(signature);
            return wechatUser;
        }
        return null;
    }

    /**
     * SHA1加密密码
     *
     * @return
     */
    public static String sha1(String psw) {
        if (StringUtils.isEmpty(psw)) {
            return null;
        } else {
            return DigestUtils.sha1Hex(psw);
        }
    }

    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    public static void main(String[] args) {

    }


}
