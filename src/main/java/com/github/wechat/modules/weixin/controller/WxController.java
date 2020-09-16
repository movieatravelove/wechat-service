package com.github.wechat.modules.weixin.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.wechat.entity.Result;
import com.github.wechat.modules.weixin.entity.WXConstants;
import com.github.wechat.modules.weixin.service.IWechatUserService;
import com.github.wechat.modules.weixin.utils.MessageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @Author: zhang
 * @Date: 2019/12/11/011 15:37
 * @Description: 微信相关
 */
@Slf4j
@Api(tags = "微信相关")
@RestController
@RequestMapping("/wx")
public class WxController {

    @Autowired
    private IWechatUserService iWechatUserService;


    @ApiOperation("授权登录")
    @GetMapping("/authorization")
    @ResponseBody
    public Result authorizationWeixin(@RequestParam String code,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        try {
            return iWechatUserService.getOauthAccessToken(code);
        } catch (Exception e) {
            log.error("RestFul of authorization is error.", e);
        }
        return new Result().error("error");
    }


    @ApiOperation("获取个人信息")
    @GetMapping("/getUserInfo")
    @ResponseBody
    public Result getUserInfo(@RequestParam String openId) {
        return iWechatUserService.getUserInfo(openId);
    }


    @ApiOperation("获取菜单")
    @ResponseBody
    @GetMapping(value = "getMenu")
    public JSONObject getMenu() {
        return iWechatUserService.getMenu();
    }


    @ApiOperation("创建二维码")
    @ResponseBody
    @GetMapping(value = "createQrCode")
    public String createQrCode(String param) {
        return iWechatUserService.createQrCode(param);
    }


    private final static String MEDIATYPE_CHARSET_JSON_UTF8 = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8";


    /**
     * 扫码带参数的二维码
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/index", method = {RequestMethod.GET, RequestMethod.POST},
            produces = MEDIATYPE_CHARSET_JSON_UTF8)
    public void get(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        //如果为get请求，则为开发者模式验证
        if ("get".equals(request.getMethod().toLowerCase())) {
            log.info("============get");
            // 签名验证
            iWechatUserService.checkSignature(request, response);
            // 生成菜单
            iWechatUserService.createCustomMenu();
        } else {
            log.info("============post");
            PrintWriter out = response.getWriter();
            try {
                Map<String, String> map = MessageUtil.xmlToMap(request);
                String ToUserName = map.get("ToUserName");
                String FromUserName = map.get("FromUserName");
                log.info("ToUserName:{},FromUserName:{}", ToUserName, FromUserName);
                String CreateTime = map.get("CreateTime");
                String MsgType = map.get("MsgType");
                // 收到用户消息必须做出回复，否则会出现【该公众号提供的服务器出现故障，请稍后再试】
                // 微信推荐直接回复 success
                String message = "success";
                log.info("MsgType:" + MsgType);
                if (MsgType.equals(WXConstants.MESSAGE_EVENT)) {
                    //从集合中，获取是哪一种事件传入
                    String eventType = map.get("Event");
                    //对获取到的参数进行处理
                    String eventKey = map.get("EventKey");
                    log.info("eventKey:{}", eventKey);
                    //扫描带参数的二维码，如果用户未关注，则可关注公众号，事件类型为subscribe；用户已关注，则事件类型为SCAN
                    if (eventType.equals(WXConstants.MESSAGE_SUBSCRIBE)) {
                        log.info("未关注 -> 已关注");
                        //  未关注 -> 已关注
                        message = MessageUtil.initText(ToUserName, FromUserName, "你好，欢迎关注！");
                    } else if (eventType.equals(WXConstants.MESSAGE_SCAN)) {
                        log.info("已关注");
                        message = MessageUtil.initText(ToUserName, FromUserName, "Hello");
                    } else if (eventType.equals(WXConstants.MESSAGE_UNSUBSCRIBE)) {
                        // TODO 取消关注 更新状态
                        log.info("取消关注");
                    }
                } else if (MsgType.equals(WXConstants.MESSAGE_TEXT)) {
                    String content = map.get("Content");
                    log.info("content:" + content);
                    if (content.contains("你好") || content.contains("hello")) {
                        message = MessageUtil.initNewsMessage(ToUserName, FromUserName);
                    } else if (content.contains("联系")) {
                        message = MessageUtil.initText(ToUserName, FromUserName,
                                "电话：123456\n" +
                                        "微信：WeChat\n" +
                                        "<a href=''>联系我们</a>");
                    } else {
                        message = MessageUtil.initText(ToUserName, FromUserName,
                                "已收到您的消息，我们将会尽快回复您。/爱心");
                    }
                }
                out.print(message); //返回转换后的XML字符串
            } catch (DocumentException e) {
                e.printStackTrace();
            }
            out.close();
        }
    }





}
