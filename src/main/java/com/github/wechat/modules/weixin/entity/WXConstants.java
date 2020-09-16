package com.github.wechat.modules.weixin.entity;

/**
 * @Author: zhang
 * @Date: 2019/12/17/017 19:22
 * @Description: 微信消息类型
 */
public class WXConstants {

    public static final String MESSAGE_TEXT = "text";//文本消息
    public static final String MESSAGE_NEWS = "news";//图文消息
    public static final String MESSAGE_IMAGE = "image";//图片消息
    public static final String MESSAGE_VOICE = "voice";//语音消息
    public static final String MESSAGE_VIDEO = "video";//视频消息
    public static final String MESSAGE_SHORTVIDEO = "shortvideo";//小视频消息
    public static final String MESSAGE_MUSIC = "music";//音乐消息
    public static final String MESSAGE_LINK = "link";//链接消息
    public static final String MESSAGE_LOCATION = "location";//上传地理位置
    public static final String MESSAGE_EVENT = "event";//事件推送
    public static final String MESSAGE_SUBSCRIBE = "subscribe";//关注
    public static final String MESSAGE_UNSUBSCRIBE = "unsubscribe";//取消关注
    public static final String MESSAGE_CLICK = "CLICK";//菜单点击——点击菜单获取消息时触发click
    public static final String MESSAGE_VIEW = "VIEW";//点击菜单跳转链接时触发view
    public static final String MESSAGE_SCAN = "SCAN";//扫描带参数二维码——未关注时触发subscribe/已关注时触发scan

}