/*
 * Copyright (C) 2017 by BIGC YSL. All rights reserved.
 */
package com.yuchen.catalog.common.utils;

import io.jsonwebtoken.*;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;

/**
 * JWT工具类
 *
 * @author yang shulin datetime:2017-02-17
 */

public class JwtUtil {

    private static final String secretKey="23yu342yiu3r52324324";


    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.getEncoder().encode(secretKey.getBytes());
        SecretKey key = new SecretKeySpec(encodedKey, "AES");
        return key;
    }

    public static void main(String[] args){
//        String jwt = createJWT("111", 20000L, "2323");
        String jwt = "eyJhbGciOiJIUzI1NiIsInR5cGUiOiJqd3QifQ.eyJpc3MiOiJ3eGwiLCJzdWIiOiJzdW4iLCJpYXQiOjE1NDc3MDYxMDAsImV4cCI6MTU0NzcxOTA2MCwia2V5IjoiNDdERTYzNkJFQUVENEQ4NkE2NUMzMDRFRDgzNTc0RUYiLCJ1c2VyTmFtZSI6InN1biJ9.cXsdwgOq_4au_nbFW5Ki0LIaF9ptwHolXyjFt5eJNQ4";
        String userName = parseJWTForGetInfo(jwt, "userName");

        int a = 0;
    }

    /**
     * 功能：创建JWT
     *
     * @param userName 用户账户
     * @param expireTime 有效时间
     * @param key 密钥
     * @return JWT串
     */
    public static String createJWT(String userName, Long expireTime,String key) {

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Date exp = new Date(nowMillis + expireTime);
        JwtBuilder builder = Jwts.builder().
                setHeaderParam("alg", "hs256").
                setHeaderParam("type", "jwt").
                setIssuer("wxl").
                setSubject(userName).
                setIssuedAt(now).
                setExpiration(exp).
                claim("key", key).
                claim("userName", userName).
                signWith(SignatureAlgorithm.HS256, generalKey());
        return builder.compact();
    }

    public String createJWT(String userName) {

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder builder = Jwts.builder().
                setHeaderParam("alg", "hs256").
                setHeaderParam("type", "jwt").
                setIssuer("rxl").
                setSubject(userName).
                setIssuedAt(now).
                signWith(SignatureAlgorithm.HS256, generalKey());
        return builder.compact();
    }


    /**
     * 功能：验证JWT是否过期
     *
     * @param userName 用户账户
     * @param jwt jwt串
     * @return 是否有效
     */
    public static boolean verifyToken(String userName, String jwt) {
        try {
            if (jwt == null || jwt.equals("")) {
                return false;
            }
            return Jwts.parser().setSigningKey(generalKey()).parseClaimsJws(jwt).getBody().getSubject().equals(userName);
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * 功能：验证JWT是否有效
     *
     * @param jwt jwt串
     * @return 是否过期
     */
    public boolean isExpire(String jwt) {
        try {
            Claims claims = Jwts.parser().setSigningKey(generalKey()).parseClaimsJws(jwt).getBody();
            Date date = claims.getExpiration();
            return date.getTime() - (new Date()).getTime() < 0;
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException ex) {
            return false;
        }
    }

    /**
     * 功能：获取其他信息
     *
     * @param token JWT串
     * @return other
     */
    public static String parseJWTForGetInfo(String token, String infoKey) {
        try {
            Claims claims = Jwts.parser().setSigningKey(generalKey()).parseClaimsJws(token).getBody();
            return claims.get(infoKey).toString();
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException ex) {
            return null;
        }
    }


    public void setSecretKey(String secretKey) {
//        this.secretKey = secretKey;
    }

    public void setExpireTime(Long expireTime) {
//        this.expireTime = expireTime;
    }
    
}
