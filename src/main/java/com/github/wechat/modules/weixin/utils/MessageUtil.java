package com.github.wechat.modules.weixin.utils;

import com.github.wechat.modules.weixin.entity.News;
import com.github.wechat.modules.weixin.entity.NewsMessage;
import com.github.wechat.modules.weixin.entity.TextMessage;
import com.github.wechat.modules.weixin.entity.WXConstants;
import com.thoughtworks.xstream.XStream;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @Author: zhang
 * @Date: 2019/12/17/017 19:31
 * @Description:
 */
public class MessageUtil {

    /**
     * 将接收到的XML格式，转化为Map对象
     *
     * @param request 将request对象，通过参数传入
     * @return 返回转换后的Map对象
     */
    public static Map<String, String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException {
        Map<String, String> map = new HashMap<String, String>();
        //从dom4j的jar包中，拿到SAXReader对象。
        SAXReader reader = new SAXReader();
        InputStream is = request.getInputStream();//从request中，获取输入流
        Document doc = (Document) reader.read(is);//从reader对象中,读取输入流
        Element root = doc.getRootElement();//获取XML文档的根元素
        List<Element> list = root.elements();//获得根元素下的所有子节点
        for (Element e : list) {
            map.put(e.getName(), e.getText());//遍历list对象，并将结果保存到集合中
        }
        is.close();
        return map;
    }

    /**
     * 初始化图文消息
     */
    public static String initNewsMessage(String toUSerName, String fromUserName) {
        List<News> newsList = new ArrayList<News>();
        NewsMessage newsMessage = new NewsMessage();
        //组建一条图文↓ ↓ ↓
        News newsItem = new News();
        newsItem.setTitle("欢迎使用");
        newsItem.setDescription("这是描述这是描述这是描述");
        newsItem.setPicUrl("https://cdn.jsdelivr.net/gh/movieatravelove/PicLibrary/wallpaper/5.jpg");
        newsItem.setUrl("www.baidu.com");
        newsList.add(newsItem);
        //组装图文消息相关信息
        newsMessage.setToUserName(fromUserName);
        newsMessage.setFromUserName(toUSerName);
        newsMessage.setCreateTime(new Date().getTime());
        newsMessage.setMsgType(WXConstants.MESSAGE_NEWS);
        newsMessage.setArticles(newsList);
        newsMessage.setArticleCount(newsList.size());
        //调用newsMessageToXml将图文消息转化为XML结构并返回
        return MessageUtil.newsMessageToXml(newsMessage);
    }

    /**
     * 图文消息转XML结构方法
     */
    public static String newsMessageToXml(NewsMessage message) {
        XStream xs = new XStream();
        //由于转换后xml根节点默认为class类，需转化为<xml>
        xs.alias("xml", message.getClass());
        xs.alias("item", new News().getClass());
        return xs.toXML(message);
    }

    /**
     * 初始化回复消息
     */
    public static String initText(String toUSerName, String fromUserName, String content) {
        TextMessage text = new TextMessage();
        text.setFromUserName(toUSerName);//原来【接收消息用户】变为回复时【发送消息用户】
        text.setToUserName(fromUserName);
        text.setMsgType(WXConstants.MESSAGE_TEXT);
        text.setCreateTime(new Date().getTime());//创建当前时间为消息时间
        text.setContent(content);
        return MessageUtil.TextMessageToXml(text);
    }

    /**
     * 将文本消息对象转化成XML格式
     *
     * @param message 文本消息对象
     * @return 返回转换后的XML格式
     */
    public static String TextMessageToXml(TextMessage message) {
        XStream xs = new XStream();
        //由于转换后xml根节点默认为class类，需转化为<xml>
        xs.alias("xml", message.getClass());
        return xs.toXML(message);
    }


    /**
     * 初始化图片消息
     */
//    public static String initImage(String toUSerName, String fromUserName, String mediaId) {
//        ImageMessage imageMessage = new ImageMessage();
//        Image image = new Image();
//        image.setMediaId("v_4wmRsJaJ9RrbKipapX8badYe1FZJFyLoFgOXGsFLwcKj567od_I1cRdCDiZSsh");//这里自己定义，可通过多媒体文件上传接口生成
//        imageMessage.setFromUserName(toUSerName);//原来【接收消息用户】变为回复时【发送消息用户】
//        imageMessage.setToUserName(fromUserName);
//        imageMessage.setMsgType(WXConstants.MESSAGE_IMAGE);
//        imageMessage.setCreateTime(new Date().getTime());//创建当前时间为消息时间
//        imageMessage.setImage(image);
//        return MessageUtil.ImageMessageToXml(imageMessage);
//    }
//
//    /**
//     * 将图片消息对象转化成XML格式
//     *
//     * @param message 图片消息对象
//     * @return 返回转换后的XML格式
//     */
//    public static String ImageMessageToXml(ImageMessage message) {
//        XStream xs = new XStream();
//        //由于转换后xml根节点默认为class类，需转化为<xml>
//        xs.alias("xml", message.getClass());
//        return xs.toXML(message);
//    }

}
