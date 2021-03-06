package com.github.wechat.modules.weixin.controller;

import com.alibaba.fastjson.JSONObject;
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
 * @Description: 微信菜单相关
 */
@Slf4j
@Api(tags = "微信菜单相关")
@RestController
@RequestMapping("/wx/menu/")
public class WxMenuController {

    @Autowired
    private IWechatUserService iWechatUserService;


    @ApiOperation("创建菜单")
    @ResponseBody
    @GetMapping(value = "create")
    public boolean create() {
        return iWechatUserService.createCustomMenu(null);
    }

    @ApiOperation("创建自定义菜单")
    @ResponseBody
    @GetMapping(value = "createByJson")
    public boolean createMenu(String json) {
        return iWechatUserService.createCustomMenu(json);
    }

    @ApiOperation("获取菜单")
    @ResponseBody
    @GetMapping(value = "get")
    public JSONObject getMenu() {
        return iWechatUserService.getMenu();
    }

}
