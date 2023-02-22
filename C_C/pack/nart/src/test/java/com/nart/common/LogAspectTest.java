package com.nart.common;

import com.nart.pojo.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LogAspectTest {

    private LogAspect logAspectUnderTest;

    private final User user = new User();

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
        MethodSignature sig = mock(MethodSignature.class);

        when(point.getSignature()).thenReturn(sig);
        when(point.getTarget()).thenReturn(user);
        when(point.getArgs()).thenReturn(new Object[]{}, new Object[]{"hello", "world"});
        when(sig.getName()).thenReturn("method Name");

        when(point.proceed()).thenReturn(null);

        // Run the test
        Object result = logAspectUnderTest.log(point);
        assertThat(result).isEqualTo(null);

        result = logAspectUnderTest.log(point);
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
