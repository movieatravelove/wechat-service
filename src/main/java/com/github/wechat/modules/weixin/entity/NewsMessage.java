package com.github.wechat.modules.weixin.entity;

import lombok.Data;

import java.util.List;

/**
 * @Author: zhang
 * @Date: 2019/12/17/017 19:38
 * @Description:
 */
@Data
public class NewsMessage extends BaseMessage {

    private int ArticleCount;//图文消息个数，限制为8条以内
    private List<News> Articles;//多条图文消息信息，默认第一个item为大图,注意，如果图文数超过8，则将会无响应

}
