package com.github.wechat.utils;

import io.jsonwebtoken.*;
import com.github.wechat.exception.ServiceException;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;


/**
 * Token工具类
 *
 **/
public class TokenUtil {

    /**
     * Token密钥
     **/
    private static final String SECRECT = "BookDIWNTMDPSLQ2547891EcvoOIDF1-232F]DRYWE=F23=-4VXCNONSFIOEIWI023";
	
    private static final int calendarField = Calendar.DATE;

    /**
     * 将密钥转换成byte数组
     **/
    public static byte[] getSecret() {
        return Base64.getEncoder().encode(SECRECT.getBytes());
    }

    public static Date getExpiresDate(int days){
    	Calendar nowTime = Calendar.getInstance();
		nowTime.add(calendarField, days);
		return nowTime.getTime();
    }
    
    /**
     * 生成Token
     **/
    public static String buildToken(Object subject, Map<String, Object> payload, Date expiresDate) {
    	Date iatDate = new Date();
        return Jwts.builder().setClaims(payload).setSubject(subject.toString()).setIssuedAt(iatDate).setExpiration(expiresDate)
                .signWith(SignatureAlgorithm.HS256, getSecret()).compact();
    }

    /**
     * 格式化Token，返回一个Claims对象
     **/
    public static Claims parse(Object subject, String token) {
        try {
            return Jwts.parser().requireSubject(subject.toString()).setSigningKey(getSecret()).parseClaimsJws(token).getBody();
        } catch (JwtException e) {
            throw new ServiceException("error Token");
        }
    }

}
