package com.nart.handler;

import com.nart.pojo.User;
import com.nart.service.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.method.HandlerMethod;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginInterceptorTest {

    class TestController {
        public void testMethod() {}
    }

    @Mock
    private LoginService mockLoginService;

    @InjectMocks
    private LoginInterceptor loginInterceptorUnderTest;

    private HandlerMethod handlerMethod;

    private MockHttpServletRequest request = new MockHttpServletRequest();

    private MockHttpServletResponse response = new MockHttpServletResponse();

    @BeforeEach
    void setup() throws NoSuchMethodException {
        Method method = TestController.class.getMethod("testMethod");
        TestController controller = new TestController();
        handlerMethod = new HandlerMethod(controller, method);
    }

    @Test
    void testPreHandle() throws Exception {
        // Configure LoginService.checkToken(...).

        // Run the test
        final boolean result1 = loginInterceptorUnderTest.preHandle(request, response, "object");
        // Verify the results
        assertThat(result1).isTrue();

        // Run the test
        final boolean result2 = loginInterceptorUnderTest.preHandle(request, response, handlerMethod);
        // Verify the results
        assertThat(result2).isFalse();

        request.addHeader("Content-Type", "text/html");
        request.addHeader("Authorization", "token");

        when(mockLoginService.checkToken("token")).thenReturn(new User());
        // Run the test
        final boolean result3 = loginInterceptorUnderTest.preHandle(request, response, handlerMethod);
        // Verify the results
        assertThat(result3).isTrue();
    }

    @Test
    void testPreHandle_LoginServiceReturnsNull() throws Exception {

        request.addHeader("Content-Type", "text/html");
        request.addHeader("Authorization", "token");

        // Run the test
        final boolean result2 = loginInterceptorUnderTest.preHandle(request, response, handlerMethod);
        // Verify the results
        assertThat(result2).isFalse();

    }

    @Test
    void testPreHandle_ThrowsException() {

        response.setWriterAccessAllowed(false);

        // Run the test
        assertThatThrownBy(() -> loginInterceptorUnderTest.preHandle(request, response, handlerMethod))
                .isInstanceOf(Exception.class);
    }

    @Test
    void testAfterCompletion() {

        // Run the test
        loginInterceptorUnderTest.afterCompletion(request, response, "handler", new Exception("message"));

        // Verify the results
    }
}
