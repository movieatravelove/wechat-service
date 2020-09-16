package com.github.wechat.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * MD5通用类
 *
 */
public class MD5Util {
    /**
     * MD5方法
     *
     * @param text 明文
     * @return 密文
     * @throws Exception
     */
    public static String md5(String text) throws Exception {
        //加密后的字符串
        String encodeStr=DigestUtils.md5Hex(text);
        System.out.println("MD5加密后的字符串为:"+encodeStr);
        return encodeStr;
    }

    /**
     * MD5验证方法
     *
     * @param text 明文
     * @param md5 密文
     * @return true/false
     * @throws Exception
     */
    public static boolean verify(String text, String md5) throws Exception {
        //根据传入的密钥进行验证
        String md5Text = md5(text);
        if(md5Text.equalsIgnoreCase(md5))
        {
            System.out.println("MD5验证通过");
            return true;
        }
        return false;
    }


    public static void main(String[] args) throws Exception {
        md5("book-dx-service");
        md5("dxzhushou-service");

        System.out.println(verify("dxzhushou-service","211c4d46b59a7fe6e39efaa62ee8fe4a"));
    }
}
