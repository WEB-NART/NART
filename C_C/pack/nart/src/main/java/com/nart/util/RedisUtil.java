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
 *  redis operations
 * @version: v1.8.0
 * @Author ZIRUI QIAO
 * @Date 2023/01/04 14:26
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
    public final static long HOUR_ONE_EXPIRE = (long) 60 * 60;
    public final static long HOUR_SIX_EXPIRE = 60 * 60 * 6L;
    public final static long MONTH_ONE_EXPIRE = 60 * 60 * 24 * 30L;
    public final static long NOT_EXPIRE = -1L;
    private final static Gson gson = new Gson();

    /**
     * Save data
     *
     * @param key redis key
     * @param value redis value
     * @param expire expire time
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
     * @param key redis key
     * @param value redis value
     */
    public void set(String key, Object value) {
        set(key, value, NOT_EXPIRE);
    }

    /**
     * Get the data and change the expiry time
     *
     * @param key redis key
     * @param expire expire time
     * @return redis value
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
     * @param key redis key
     * @param clazz class of return value
     * @param <T> pojo class
     * @return: T
     */
    public <T> T get(String key, Class<T> clazz) {
        return get(key, clazz, NOT_EXPIRE);
    }


    /**
     * Access to data
     *
     * @param key redis key
     * @return
     */
    public Object get(String key) {
        return get(key, NOT_EXPIRE);
    }

    /**
     * Delete the specified key
     *
     * @param key redis key
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * Bulk delete
     *
     * @param keys a list of redis keys
     */
    public void delete(Collection<String> keys) {
        redisTemplate.delete(keys);
    }

    /**
     * Modify expiry time
     *
     * @param key redis key
     * @param expire expire time
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