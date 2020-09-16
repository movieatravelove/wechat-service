package com.github.wechat.modules.weixin.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Author: zhang
 * @Date: 2019/12/12/012 11:29
 * @Description:
 */
@Data
public class AccessTokenDO {

    private int id;
    // 用户标识
    private String openid;
    // 网页授权接口调用凭证
    private String accessToken;
    // 用于刷新凭证
    private String refreshToken;
    // 凭证有效时长
    private String expiresIn;
    // 用户授权作用域
    private String scope;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}
