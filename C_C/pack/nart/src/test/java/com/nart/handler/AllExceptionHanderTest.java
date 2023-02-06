package com.nart.handler;

import com.nart.util.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AllExceptionHanderTest {

    private AllExceptionHander allExceptionHanderUnderTest;

    @BeforeEach
    void setUp() {
        allExceptionHanderUnderTest = new AllExceptionHander();
    }

    @Test
    void testDoException() {
        // Setup
        final Result expectedResult = new Result(false, 99999, "undefined error", null);

        // Run the test
        final Result result = allExceptionHanderUnderTest.doException(new Exception("message"));

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }
}
