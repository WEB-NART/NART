package com.nart.config;

import com.nart.handler.LoginInterceptor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

@ExtendWith(MockitoExtension.class)
class WebMVCConfigTest {

    @Mock
    private LoginInterceptor mockLoginInterceptor;

    @InjectMocks
    private WebMVCConfig webMVCConfigUnderTest;

    @Test
    void testAddCorsMappings() {
        // Setup
        final CorsRegistry registry = new CorsRegistry();

        // Run the test
        webMVCConfigUnderTest.addCorsMappings(registry);

        // Verify the results
    }

    @Test
    void testAddInterceptors() {
        // Setup
        final InterceptorRegistry registry = new InterceptorRegistry();

        // Run the test
        webMVCConfigUnderTest.addInterceptors(registry);

        // Verify the results
    }
}
