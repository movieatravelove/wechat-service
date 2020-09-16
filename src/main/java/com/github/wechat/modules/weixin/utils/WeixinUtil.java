package com.github.wechat.modules.weixin.utils;

import com.alibaba.fastjson.JSONObject;
import com.github.wechat.exception.ServiceException;
import com.github.wechat.modules.weixin.entity.AccessTokenDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;


/**
 * @Author: zhang
 * @Date: 2019/12/17/017 10:04
 * @Description:
 */
@Service
public class WeixinUtil {

    private static Logger log = LoggerFactory.getLogger(WeixinUtil.class);

    // 获取access_token的接口地址（GET） 限200（次/天）
    @Value("${app.WX_ACCESS_TOKEN}")
    private String WX_ACCESS_TOKEN_URL;
    @Value("${app.WX_SEND_TEMPLATE_MESSAGE}")
    private String SEND_TEMPLATE_URL;

    @Autowired
    private RestTemplate restTemplate;


    /**
     * 获取access_token
     *
     * @param appid     凭证
     * @param appsecret 密钥
     * @return
     */
    public AccessTokenDO getAccessTokenFromWX(String appid, String appsecret) {
        AccessTokenDO accessToken = null;
        String requestUrl = WX_ACCESS_TOKEN_URL.replace("APPID", appid).replace("APPSECRET", appsecret);
        String data = restTemplate.getForObject(requestUrl, String.class);
        JSONObject jsonObject = JSONObject.parseObject(data);
        // 如果请求成功
        if (null == jsonObject || jsonObject.containsKey("errcode")) {
            log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInteger("errcode"),
                    jsonObject.getString("errmsg"));
            throw new ServiceException("获取accessToken失败");
        } else {
            accessToken = new AccessTokenDO();
            accessToken.setAccessToken(jsonObject.getString("access_token"));
            accessToken.setExpiresIn(String.valueOf(jsonObject.get("expires_in")));
            accessToken.setCreateTime(new Date());
        }
        return accessToken;
    }


    /**
     * 发送模板消息
     *
     * @param accessToken
     * @param data
     *
     * 恭喜你！购买成功啦！快快去使用吧！
     * 服务名称
     * 订单编号
     * 支付金额
     * 联系电话
     * 适用门店
     * 请您注意过期时间，及时使用呦~
     */
    public JSONObject sendTemplateMessage(String accessToken, String data){
        String requestUrl = SEND_TEMPLATE_URL.replace("ACCESS_TOKEN", accessToken);
        JSONObject object = restTemplate.postForObject(requestUrl, JSONObject.parse(data), JSONObject.class);
        return object;
    }


}