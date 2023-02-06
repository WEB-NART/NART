package com.nart.common;

import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LogAspectTest {

    private LogAspect logAspectUnderTest;

    @BeforeEach
    void setUp() {
        logAspectUnderTest = new LogAspect();
    }

    @Test
    void testPt() {
        // Setup
        // Run the test
        logAspectUnderTest.pt();

        // Verify the results
    }

    @Test
    void testLog() throws Throwable {
        // Setup
        ProceedingJoinPoint point = mock(ProceedingJoinPoint.class);

        when(point.proceed()).thenReturn(null);

        // Run the test
        final Object result = logAspectUnderTest.log(point);
        // Verify the results
        assertThat(result).isEqualTo(null);
    }

    @Test
    void testLog_ThrowsThrowable() {
        // Setup
        final ProceedingJoinPoint point = null;

        // Run the test
        assertThatThrownBy(() -> logAspectUnderTest.log(point)).isInstanceOf(Throwable.class);
    }
}
