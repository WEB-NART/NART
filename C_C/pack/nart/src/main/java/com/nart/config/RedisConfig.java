package com.nart.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
//import com.alibaba.fastjson2.support.spring.data.redis.FastJsonRedisSerializer; 用这个时报错
//import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: pack
 *
 * @className: RedisConfig
 *  Redis configuration file
 * @version: v1.8.0
 * @Author ZIRUI QIAO
 * @Date 2023/1/10 14:22
 */
@Configuration
public class RedisConfig {
    @Resource
    private RedisConnectionFactory factory;

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new FastJsonRedisSerializer<>(Object.class));
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new FastJsonRedisSerializer<>(Object.class));
        redisTemplate.setConnectionFactory(factory);

        return redisTemplate;
    }
}