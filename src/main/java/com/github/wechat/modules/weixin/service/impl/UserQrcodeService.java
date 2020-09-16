package com.github.wechat.modules.weixin.service.impl;

import com.github.wechat.utils.FileUtils;
import com.github.wechat.utils.ImagesUtils;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * @Author: zhang
 * @Date: 2019/12/30/030 11:24
 * @Description: 生成 二维码 + 头像
 */
@Service
public class UserQrcodeService {

    public static final String FORMAT = ".jpg";


    /**
     * 合成二维码 + 头像
     *
     * @param qrCodeImg
     * @param avatarImg
     */
    public static String composeImage(String fileName, String qrCodeImg, String avatarImg) {
        //创建图片
        BufferedImage img = new BufferedImage(430, 430, BufferedImage.TYPE_INT_RGB);
        try {
            //读取互联网图片
            BufferedImage qrCode = ImageIO.read(new URL(qrCodeImg));
            BufferedImage avatar = ImageIO.read(new URL(avatarImg));
            avatar = ImagesUtils.setRadius(avatar);
            //开启画图
            Graphics g = img.getGraphics();
            // 绘制缩小后的图
            g.drawImage(qrCode.getScaledInstance(430, 430, Image.SCALE_DEFAULT), 0, 0, null);
            g.drawImage(avatar.getScaledInstance(90, 90, Image.SCALE_DEFAULT), 170, 170, null);
            // 在图上写字
            //g.setColor(Color.black);
            //g.setFont(new Font("微软雅黑", Font.PLAIN, 16));
            //g.drawString("扫码关注公众号 享受更多福利", 100, 420);
            // 关闭g
            g.dispose();
            // 写到本地图片
            String path = FileUtils.getPath();
            fileName = fileName + FORMAT;
            ImageIO.write(img, "jpg", new File(path + fileName));
            // 上传oss
            return uploadOSS(fileName, path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 上传 OSS
     *
     * @param fileName
     * @return
     */
    private static String uploadOSS(String fileName, String path) {
        // TODO 上传图片
        return null;
    }


    public static void main(String[] args) {

    }
}
