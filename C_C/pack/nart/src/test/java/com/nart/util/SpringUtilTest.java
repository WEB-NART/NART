package com.nart.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class SpringUtilTest {

    @Autowired
    private SpringUtil springUtilUnderTest;

    @Mock
    private ApplicationContext context;


    @BeforeEach
    void setUp() {
        springUtilUnderTest = new SpringUtil();
    }

    @Test
    void testSetup() {
        springUtilUnderTest.setApplicationContext(context);
    }


    @Test
    void testGetApplicationContext() {
        // Setup
        // Run the test
        springUtilUnderTest.setApplicationContext(context);
        final ApplicationContext result = SpringUtil.getApplicationContext();
        //assertThat(result).isNotNull();
    }

    @Test
    void testGetBean1() {
        Object userService = SpringUtil.getBean("groupServiceImpl");
        assertThat(userService).isNotNull();
    }
}
