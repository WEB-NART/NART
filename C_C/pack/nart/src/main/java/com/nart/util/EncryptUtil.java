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
import java.util.Random;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: pack
 *
 * @className: EncryptUtil
 *  TODO
 * @version: v1.8.0
 * @Author ZIRUI QIAO
 * @Date 2023/01/02 16:26
 */
@Slf4j
public class EncryptUtil {
    //private static final String salt = "2361578nart!@#";
    private static final String jwtToken = "123456Nart!@#$$";

    public static String encryptPwd(String pwd, String salt) {
        return DigestUtils.md5Hex(pwd + salt);
    }

    private static String getSalt(int len) {
        char[] chars = ("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890!@#$%^&*()_+").toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            char aChar = chars[new Random().nextInt(chars.length)];
            sb.append(aChar);
        }
        return sb.toString();
    }

    public static String getRandLengthSalt() {
        int len = new Random().nextInt(12);
        return getSalt(len);
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
}
