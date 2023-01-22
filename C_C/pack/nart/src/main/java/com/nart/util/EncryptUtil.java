package com.nart.util;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: pack
 *
 * @className: EncryptUtil
 *  TODO
 * @version: v1.8.0
 * @Author ZIRUI QIAO
 * @Date 2022/8/30 16:26
 */
@Slf4j
public class EncryptUtil {
    private static final String salt = "2361578nart!@#";
    private static final String jwtToken = "123456Nart!@#$$";

    public static String encryptPwd(String pwd) {
        return DigestUtils.md5Hex(pwd + salt);
    }

    public static String createToken(Long userId){
        Map<String,Object> claims = new HashMap<>();
        claims.put("userId",userId);
        JwtBuilder jwtBuilder = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, jwtToken) // assign token, key is jwtToken
                .setClaims(claims) // use userID as body data
                .setIssuedAt(new Date()) // set create time
                .setExpiration(new Date(System.currentTimeMillis() + 24L * 60 * 60 * 60 * 1000));// one day available
        String token = jwtBuilder.compact();
        return token;
    }

    public static Map<String, Object> checkToken(String token){
//        log.info("checkToken: " + token);
        try {
            Jwt parse = Jwts.parser().setSigningKey(jwtToken).parse(token);
//            log.info("jwt header: " + GsonFormatter.toJsonString(parse.getHeader()));
//            log.info("jwt body: " + GsonFormatter.toJsonString(parse.getBody()));
            return (Map<String, Object>) parse.getBody();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }

    public static void main(String[] args) {
        String token = EncryptUtil.createToken(100L);
        System.out.println(token);
        Map<String, Object> map = EncryptUtil.checkToken(token);
        System.out.println(map.get("userId"));
        System.out.println("9ef18e57327e49d5d09aba8f8e58e2b9");
        System.out.println("1452a94dfb8d4c81b08c88bf3565490a");
        System.out.println(encryptPwd("123457"));
    }
}
