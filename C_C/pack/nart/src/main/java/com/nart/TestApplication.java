package com.nart;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@MapperScan("com.nart.dao")
public class TestApplication {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(TestApplication.class, args);
//        FakeDataGenerator fdg = new FakeDataGenerator();
//        fdg.generateTestData(1);
    }

}
