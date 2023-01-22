package com.nart.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@Rollback
@Transactional
class DataCounterServiceTest {

    @Autowired
    private DataCounterService dataCounterService;
    @Test
    void updateUserAmount() {
        int i = dataCounterService.updateUserAmount(false);
        System.out.println(i);
        assertEquals(2,i);
    }

    @Test
    void updateOnlineUserAmount() {
        int i = dataCounterService.updateOnlineUserAmount(false);
        System.out.println(i);
        assertEquals(2,i);
    }

    @Test
    void updateStatusAmount() {
        int i = dataCounterService.updateStatusAmount(false);
        System.out.println(i);
        assertEquals(2,i);
    }

    @Test
    void updateCommentAmount() {
        int i = dataCounterService.updateCommentAmount(false);
        System.out.println(i);
        assertEquals(2,i);
    }

    @Test
    void updateMessageAmount() {
        int i = dataCounterService.updateMessageAmount(false);
        System.out.println(i);
        assertEquals(2,i);
    }
}