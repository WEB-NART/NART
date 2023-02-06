package com.nart.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@ExtendWith(MockitoExtension.class)
class RedisConfigTest {

    @Mock
    private RedisConnectionFactory mockFactory;

    @InjectMocks
    private RedisConfig redisConfigUnderTest;

    @Test
    void testRedisTemplate() {
        // Setup
        // Run the test
        final RedisTemplate<String, Object> result = redisConfigUnderTest.redisTemplate();

        // Verify the results
    }
}
