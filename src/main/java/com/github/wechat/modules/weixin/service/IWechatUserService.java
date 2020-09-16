package com.github.wechat.modules.weixin.service;

import com.alibaba.fastjson.JSONObject;
import com.github.wechat.entity.Result;
import com.github.wechat.modules.weixin.entity.AccessTokenDO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: zhang
 * @Date: 2019/12/12/012 11:09
 * @Description:
 */
public interface IWechatUserService {

    /**
     * 根据code获取授权的token
     * 仅限授权时使用，与全局的access_token不同
     *
     * @param code
     * @return String 用户信息
     */
    Result getOauthAccessToken(String code);


    /**
     * 签名验证
     * @param request
     * @param response
     */
    void checkSignature(HttpServletRequest request, HttpServletResponse response);


    Result getUserInfo(String openId);

    /**
     * 获取基础支持中的 accessToken
     * @return
     */
    AccessTokenDO getAccessToken();

    /**
     * 创建自定义菜单
     * @return
     */
    boolean createCustomMenu();

    /**
     * 查询自定义菜单
     * @return
     */
    JSONObject getMenu();


    String createQrCode(String param);



}
