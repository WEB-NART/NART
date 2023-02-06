package com.nart.util;

import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: pack
 *
 * @className: RedisUtil
 *  TODO
 * @version: v1.8.0
 * @Author ZIRUI QIAO
 * @Date 2022/9/2 14:26
 */
@Component
public class RedisUtil {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    /**
     * Default expiry time is 24 hours in seconds
     */
    public final static long DEFAULT_EXPIRE = 60 * 60 * 24L;
    /**
     * Expires in 1 hour, in seconds
     */
    public final static long HOUR_ONE_EXPIRE = 60 * 60 * 1L;
    /**
     * Expiry time is 6 hours in seconds
     */
    public final static long HOUR_SIX_EXPIRE = 60 * 60 * 6L;
    /**
     * Expires in 1 month, in seconds
     */
    public final static long MONTH_ONE_EXPIRE = 60 * 60 * 24 * 30L;
    /**
     * No expiry time set
     */
    public final static long NOT_EXPIRE = -1L;
    /**
     * json conversion
     */
    private final static Gson gson = new Gson();

    /**
     * Save data
     *
     * @param key
     * @param value
     * @param expire
     */
    public void set(String key, Object value, long expire) {
        if (!StringUtils.isBlank(key) && value != null) {
            redisTemplate.opsForValue().set(key, value);
        }
        if (expire != NOT_EXPIRE) {
            expire(key, expire);
        }
    }

    /**
     * Save data Expires by default one day
     *
     * @param key
     * @param value
     */
    public void set(String key, Object value) {
        set(key, value, NOT_EXPIRE);
    }

    /**
     * Get the data and change the expiry time
     *
     * @param key
     * @param expire
     * @return
     */
    public Object get(String key, long expire) {
        Object value = redisTemplate.opsForValue().get(key);
        if (expire != NOT_EXPIRE) {
            expire(key, expire);
        }
        return value;
    }

    public <T> T get(String key, Class<T> clazz, long expire) {
        String value = stringRedisTemplate.opsForValue().get(key);
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value == null ? null : fromJson(value, clazz);
    }

    /**
     * Access to data
     * @param key
     * @param clazz
     * @param <T> pojo class
     * @return: T
     * @Author: Zirui Qiao
     * @Date: 2023-01-08 5:59 p.m.
     */
    public <T> T get(String key, Class<T> clazz) {
        return get(key, clazz, NOT_EXPIRE);
    }


    /**
     * Access to data
     *
     * @param key
     * @return
     */
    public Object get(String key) {
        return get(key, NOT_EXPIRE);
    }

    /**
     * Delete the specified key
     *
     * @param key
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * Bulk delete
     *
     * @param keys
     */
    public void delete(Collection<String> keys) {
        redisTemplate.delete(keys);
    }

    /**
     * Modify expiry time
     *
     * @param key
     * @param expire
     */
    public void expire(String key, long expire) {
        redisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    /**
     * JSON data, converted to Object
     */
    private <T> T fromJson(String json, Class<T> clazz){
        return gson.fromJson(json, clazz);
    }
}