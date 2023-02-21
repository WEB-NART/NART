package com.nart.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class ProgressBarTest {

    private ProgressBar pb;

    @BeforeEach
    public void setUp() throws IOException {
        pb = new ProgressBar();
    }

    @AfterEach
    public void tearDown() {
        pb = null;
    }


    @Test
    void load() throws InterruptedException {
        pb.printProgress();
        for (int i = 0; i <= 101; i++) {
            Thread.sleep(10);
            pb.load();
        }
    }
}