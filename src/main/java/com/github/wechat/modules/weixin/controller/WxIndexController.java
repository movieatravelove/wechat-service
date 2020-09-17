package com.github.wechat.modules.weixin.controller;

import com.github.wechat.entity.Result;
import com.github.wechat.modules.weixin.service.IWechatUserService;
import com.github.wechat.modules.weixin.utils.WXSignUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author: zhang
 * @Date: 2019/12/11/011 15:37
 * @Description: 微信用户相关
 */
@Slf4j
@Api(tags = "微信用户相关")
@RestController
@RequestMapping("/wx")
public class WxIndexController {

    @Value("${app.WX_TOKEN}")
    private String WX_TOKEN;

    private final static String MEDIATYPE_CHARSET_JSON_UTF8 =
            MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8";

    @Autowired
    private IWechatUserService iWechatUserService;


    /**
     * 签名验证，在微信后台配置改路径
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @GetMapping(value = "/index", produces = MEDIATYPE_CHARSET_JSON_UTF8)
    public void get(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("============get");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        //如果为get请求，则为签名验证
        checkSignature(request, response);
    }


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



    /**
     * 签名验证
     *
     * @param request
     * @param response
     */
    public void checkSignature(HttpServletRequest request, HttpServletResponse response) {
        String signature = request.getParameter("signature");/// 微信加密签名
        String timestamp = request.getParameter("timestamp");/// 时间戳
        String nonce = request.getParameter("nonce"); /// 随机数
        String echostr = request.getParameter("echostr"); // 随机字符串
        try {
            PrintWriter out = response.getWriter();
            if (WXSignUtil.checkSignature(signature, WX_TOKEN, timestamp, nonce)) {
                log.info("校验成功！");
                out.print(echostr);
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
