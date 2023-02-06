package com.nart.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RedisUtilTest {

    @Mock
    private RedisTemplate<String, Object> mockRedisTemplate;
    @Mock
    private StringRedisTemplate mockStringRedisTemplate;
    @Mock
    private ValueOperations<String, Object> map;

    @InjectMocks
    private RedisUtil redisUtilUnderTest;

    @Test
    void testSet1() {
        // Setup
        when(mockRedisTemplate.opsForValue()).thenReturn(map);
        when(mockRedisTemplate.expire("key", 0L, TimeUnit.SECONDS)).thenReturn(false);

        // Run the test
        redisUtilUnderTest.set("key", "value", 0L);

        // Verify the results
        verify(mockRedisTemplate).expire("key", 0L, TimeUnit.SECONDS);
    }

    @Test
    void testSet2() {
        // Run the test
        redisUtilUnderTest.set("", "value");
        redisUtilUnderTest.set("key", null);
    }

    @Test
    void testGet1() {
        // Setup
        when(mockRedisTemplate.opsForValue()).thenReturn(map);
        when(mockRedisTemplate.expire("key", 0L, TimeUnit.SECONDS)).thenReturn(false);

        // Run the test
        final Object result = redisUtilUnderTest.get("key", 0L);

        // Verify the results
        verify(mockRedisTemplate).expire("key", 0L, TimeUnit.SECONDS);
    }

    @Test
    void testGet2() {
        // Setup
        ValueOperations mock = mock(ValueOperations.class);
        when(mockStringRedisTemplate.opsForValue()).thenReturn(mock);
        when(mock.get("key")).thenReturn("result");
        when(mockRedisTemplate.expire("key", 0L, TimeUnit.SECONDS)).thenReturn(false);

        // Run the test
        final String result = redisUtilUnderTest.get("key", String.class, 0L);
        // Verify the results
        assertThat(result).isEqualTo("result");
        verify(mockRedisTemplate).expire("key", 0L, TimeUnit.SECONDS);

        when(mock.get("key")).thenReturn(null);

        // Run the test
        final String result2 = redisUtilUnderTest.get("key", String.class, 0L);
        // Verify the results
        assertThat(result2).isEqualTo(null);
    }

    @Test
    void testGet3() {
        // Setup
        ValueOperations mock = mock(ValueOperations.class);
        when(mockStringRedisTemplate.opsForValue()).thenReturn(mock);
        when(mock.get("key")).thenReturn("result");

        // Run the test
        final String result = redisUtilUnderTest.get("key", String.class);
        // Verify the results
        assertThat(result).isEqualTo("result");
    }

    @Test
    void testGet4() {
        // Setup
        when(mockRedisTemplate.opsForValue()).thenReturn(map);
        when(map.get("key")).thenReturn("value");

        // Run the test
        final Object result = redisUtilUnderTest.get("key");
        // Verify the results
        assertThat(result).isEqualTo("value");
    }

    @Test
    void testDelete1() {
        // Setup
        when(mockRedisTemplate.delete("key")).thenReturn(false);

        // Run the test
        redisUtilUnderTest.delete("key");

        // Verify the results
        verify(mockRedisTemplate).delete("key");
    }

    @Test
    void testDelete2() {
        // Setup
        when(mockRedisTemplate.delete(Arrays.asList("value"))).thenReturn(0L);

        // Run the test
        redisUtilUnderTest.delete(Arrays.asList("value"));

        // Verify the results
        verify(mockRedisTemplate).delete(Arrays.asList("value"));
    }

    @Test
    void testExpire() {
        // Setup
        when(mockRedisTemplate.expire("key", 0L, TimeUnit.SECONDS)).thenReturn(false);

        // Run the test
        redisUtilUnderTest.expire("key", 0L);

        // Verify the results
        verify(mockRedisTemplate).expire("key", 0L, TimeUnit.SECONDS);
    }
}
