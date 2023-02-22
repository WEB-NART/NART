package com.nart.util;

import com.nart.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
class SpringUtilTest {

    private SpringUtil springUtilUnderTest;

    @Mock
    private ApplicationContext context;

    @MockBean
    private UserService Name;

    @InjectMocks
    private String name = "result";


    @BeforeEach
    void setUp() {
        springUtilUnderTest = new SpringUtil();
    }


    @Test
    void testGetApplicationContext() {
        // Setup
        // Run the test
        springUtilUnderTest.setApplicationContext(context);
        final ApplicationContext result = SpringUtil.getApplicationContext();
    }

    @Test
    void testGetBean1() {
        given(this.context.getBean("UserService")).willReturn(this.Name);
        springUtilUnderTest.setApplicationContext(context);
        SpringUtil.getBean("UserService");
    }
}
