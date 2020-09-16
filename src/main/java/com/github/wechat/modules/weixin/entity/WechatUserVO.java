package com.github.wechat.modules.weixin.entity;

import lombok.Data;

/**
 * @Author: zhang
 * @Date: 2019/12/12/012 11:26
 * @Description:
 */
@Data
public class WechatUserVO {

    private Long userId;
    // 用户标识
    private String openid;
    // 昵称
    private String nickname;
    // 头像
    private String headImgUrl;
    // 会员码
    private String qrCodeImg;
    // token
    private String token;
    private Long expiresDate;
    // 必填，生成签名的时间戳
    private long timestamp;
    // 必填，生成签名的随机串
    private String nonceStr;
    // 签名
    private String signature;

}
