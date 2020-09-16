package com.github.wechat.utils;

/**
 * 随机数工具类
 *
 * @author liuyazhuang
 */
public final class RandomUtils {

    public static void main(String[] args) {
        System.out.println(getRandom(12));
    }

    /**
     * 获取指定位数的随机数
     *
     * @param len
     * @return
     */
    public static String getRandom(int len) {
        len = len > 0 ? len : 32;
        String str = "ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz0123456789";
        int maxPos = str.length();
        String pwd = "";
        for (int i = 0; i < len; i++) {
            pwd += str.charAt((int) (Math.floor(Math.random() * maxPos)));
        }
        return pwd;
    }

    public static String createOrderId(String machineId) {
        String orderId = machineId + (System.currentTimeMillis() + "").substring(1)
                + (System.nanoTime() + "").substring(7, 10);
        return orderId;
    }

    public static String createUUID(int len) {
        String uuid = java.util.UUID.randomUUID().toString()
                .replaceAll("-", "");
        return uuid.substring(0, len);
    }
}  