package com.github.wechat.modules.weixin.entity;

import lombok.Data;

/**
 * @Author: zhang
 * @Date: 2019/12/17/017 19:23
 * @Description: 消息实体公共
 */
@Data
public class BaseMessage {

    private String ToUserName;
    private String FromUserName;
    private Long CreateTime;
    private String MsgType;

}
