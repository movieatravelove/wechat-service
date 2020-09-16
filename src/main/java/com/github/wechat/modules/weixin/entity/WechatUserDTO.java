package com.github.wechat.modules.weixin.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Author: zhang
 * @Date: 2019/12/12/012 11:24
 * @Description:
 */
@Data
public class WechatUserDTO {

    private Long id;
    // 用户标识
    private String openid;
    // 昵称
    private String nickname;
    // 性别（1是男性，2是女性，0是未知）
    private int sex;
    // 头像连接
    private String headImgUrl;
    // 微信号
    private String wechatNo;
    // 国家
    private String country;
    // 省份
    private String province;
    // 城市
    private String city;
    // 状态 0:正常  1：注销
    private int status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastLoginTime;
    private int count; //mybatis判断记录是否存在



}
