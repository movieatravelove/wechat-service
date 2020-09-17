package com.github.wechat.modules.weixin.controller;

import com.github.wechat.modules.weixin.service.IWechatUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: zhang
 * @Date: 2019/12/11/011 15:37
 * @Description: 微信二维码相关
 */
@Slf4j
@Api(tags = "微信二维码相关")
@RestController
@RequestMapping("/wx/code/")
public class WxCodeController {

    @Autowired
    private IWechatUserService iWechatUserService;


    @ApiOperation("创建带参二维码")
    @ResponseBody
    @GetMapping(value = "create")
    public String createQrCode(String param) {
        return iWechatUserService.createQrCode(param);
    }


}
