package com.nart.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Executor;

class ThreadPoolConfigTest {

    private ThreadPoolConfig threadPoolConfigUnderTest;

    @BeforeEach
    void setUp() {
        threadPoolConfigUnderTest = new ThreadPoolConfig();
    }

    @Test
    void testAsyncServiceExecutor() {
        // Setup
        // Run the test
        final Executor result = threadPoolConfigUnderTest.asyncServiceExecutor();

        // Verify the results
    }
}
