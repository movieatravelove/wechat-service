package com.github.wechat.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * @Author: zhang
 * @Date: 2019/12/30/030 10:51
 * @Description:
 */
public class ImagesUtils {

    /**
     * 图片设置圆角
     *
     * @param srcImage
     * @param radius
     * @param border
     * @param padding
     * @return
     * @throws IOException
     */
    public static BufferedImage setRadius(BufferedImage srcImage, int radius, int border, int padding) throws IOException {
        int width = srcImage.getWidth();
        int height = srcImage.getHeight();
        int canvasWidth = width + padding * 2;
        int canvasHeight = height + padding * 2;

        BufferedImage image = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D gs = image.createGraphics();
        gs.setComposite(AlphaComposite.Src);
        gs.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gs.setColor(Color.WHITE);
        gs.fill(new RoundRectangle2D.Float(0, 0, canvasWidth, canvasHeight, radius, radius));
        gs.setComposite(AlphaComposite.SrcAtop);
        gs.drawImage(setClip(srcImage, radius), padding, padding, null);
        if (border != 0) {
            gs.setColor(Color.GRAY);
            gs.setStroke(new BasicStroke(border));
            gs.drawRoundRect(padding, padding, canvasWidth - 2 * padding, canvasHeight - 2 * padding, radius, radius);
        }
        gs.dispose();
        return image;
    }

    /**
     * 图片设置圆角
     *
     * @param srcImage
     * @return
     * @throws IOException
     */
    public static BufferedImage setRadius(BufferedImage srcImage) throws IOException {
        int radius = (srcImage.getWidth() + srcImage.getHeight()) / 12;
        return setRadius(srcImage, radius, 2, 4);
    }

    /**
     * 图片切圆角
     *
     * @param srcImage
     * @param radius
     * @return
     */
    public static BufferedImage setClip(BufferedImage srcImage, int radius) {
        int width = srcImage.getWidth();
        int height = srcImage.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gs = image.createGraphics();

        gs.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gs.setClip(new RoundRectangle2D.Double(0, 0, width, height, radius, radius));
        gs.drawImage(srcImage, 0, 0, null);
        gs.dispose();
        return image;
    }

    public static void main(String[] args) {
        try {
            BufferedImage avatar = ImageIO.read(new URL("http://thirdwx.qlogo.cn/mmopen/vi_32/NJRwoVffQWWDwRxXpF3Z5ktIUibU5UQxibHL4V8wibTLwT8ic4enqicAiawmGhSIeHrrUWWTwKZcichLCTLdluoUH0Ryw/132"));
            BufferedImage img = setRadius(avatar);
            ImageIO.write(img, "jpg", new File("E:\\test1.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
