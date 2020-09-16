package com.github.wechat.modules.weixin.entity;

import lombok.Data;

/**
 * @Author: zhang
 * @Date: 2019/12/17/017 19:24
 * @Description: 文本消息实体
 */
@Data
public class TextMessage extends BaseMessage{

    private String Content;
    private String MsgId;

}
