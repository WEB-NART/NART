package com.nart.util;

import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
     * Matching query e.g. test* Query all keys starting with test
     *
     * @param pattern
     * @return
     */
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * Match Delete test* Delete all keys starting with test
     *
     * @param pattern
     */
    public void deleteByPattern(String pattern) {
        redisTemplate.delete(keys(pattern));
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
     * Get the specified key-value pair
     *
     * @param key
     * @param field
     * @return
     */
    public Object hGet(String key, String field) {
        return redisTemplate.opsForHash().get(key, field);
    }

    /**
     * Get all key-value pairs
     *
     * @param key
     * @return
     */
    public Map<String, Object> hGetAll(String key) {
        HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
        return hashOperations.entries(key);
    }

    public Set<Object> hGetField(String key) {
        Set<Object> fieldSet = redisTemplate.opsForHash().keys(key);
        return fieldSet;
    }

    /**
     * Batch save Default one day expiry
     *
     * @param key
     * @param map
     */
    public void hMSet(String key, Map<String, Object> map) {
        hMSet(key, map, NOT_EXPIRE);
    }

    /**
     * Batch saving Customised expiry times
     *
     * @param key
     * @param map
     * @param expire
     */
    public void hMSet(String key, Map<String, Object> map, long expire) {
        redisTemplate.opsForHash().putAll(key, map);

        if (expire != NOT_EXPIRE) {
            expire(key, expire);
        }
    }

    /**
     * Add a new hashMap value Default expires in one day
     *
     * @param key
     * @param field
     * @param value
     */
    public void hSet(String key, String field, Object value) {
        hSet(key, field, value, NOT_EXPIRE);
    }

    /**
     * New hashMap value Custom expiry time
     *
     * @param key
     * @param field
     * @param value
     * @param expire
     */
    public void hSet(String key, String field, Object value, long expire) {
        redisTemplate.opsForHash().put(key, field, value);

        if (expire != NOT_EXPIRE) {
            expire(key, expire);
        }
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
     * Deleting key-value pairs from variables
     *
     * @param key
     * @param fields
     */
    public void hDel(String key, Object... fields) {
        redisTemplate.opsForHash().delete(key, fields);
    }

    /**
     * Add element value to the left Default day expires
     *
     * @param key
     * @param value
     */
    public void leftPush(String key, Object value) {
        leftPush(key, value, NOT_EXPIRE);
    }

    /**
     * Add element value on the left Customize expiry time
     *
     * @param key
     * @param value
     * @param expire
     */
    public void leftPush(String key, Object value, long expire) {
        redisTemplate.opsForList().leftPush(key, value);

        if (expire != NOT_EXPIRE) {
            expire(key, expire);
        }
    }

    /**
     * Remove the element to the right of the set
     *
     * @param key
     * @return
     */
    public Object rightPop(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    /**
     * Add new member to Set
     *
     * @param key
     * @param value
     */
    public void addToSet(String key, long expire, String... value) {
        stringRedisTemplate.opsForSet().add(key, value);

        if (expire != NOT_EXPIRE) {
            expire(key, expire);
        }
    }

    /**
     * Delete the corresponding member of a Set
     *
     * @param key
     * @param value
     */
    public void removeFromSet(String key, String value) {
        stringRedisTemplate.opsForSet().remove(key, value);
    }

    /**
     * Get all members of Set
     *
     * @param key
     * @return
     */
    public Set<String> membersOfSet(String key) {
        Set<String> members = stringRedisTemplate.opsForSet().members(key);
        return members;
    }

    /**
     * Gets whether a Set contains members
     *
     * @param key
     * @param value
     * @return
     */
    public boolean setContainsWarn(String key, String value) {
        return stringRedisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * Add to set
     *
     * @param key
     * @param value
     * @param expire
     */
    public void addSet(String key, Object value, long expire) {
        redisTemplate.opsForSet().add(key, value);
        if (expire != NOT_EXPIRE) {
            expire(key, expire);
        }
    }

    /**
     * Query set by key
     *
     * @param key
     */
    public Set<Object> members(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * Delete the corresponding member of a Set
     *
     * @param key
     * @param value
     */
    public void removeOfSet(String key, Object value) {
        stringRedisTemplate.opsForSet().remove(key, value);
    }

    public long inc(String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    public List<Object> getAllByKey(String keysPattern) {
        Set<String> keys = redisTemplate.keys(keysPattern);
        return redisTemplate.opsForValue().multiGet(keys);
    }
    /**
     * JSON data, converted to Object
     */
    private <T> T fromJson(String json, Class<T> clazz){
        return gson.fromJson(json, clazz);
    }
}