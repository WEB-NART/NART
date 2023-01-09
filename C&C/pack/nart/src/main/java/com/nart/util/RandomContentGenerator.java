package com.nart.util;

import com.github.javafaker.Faker;

import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: pack
 *
 * @className: RandomContentGenerator
 *  TODO
 * @version: v1.8.0
 * @Author ZIRUI QIAO
 * @Date 2022/8/30 17:45
 */
public class RandomContentGenerator {

    private static final Faker faker = new Faker(Locale.CANADA);
    public static String getRandomId(List<String> ids) {
        Random r = new Random();
        int randomInt = r.nextInt(ids.size());
        return ids.get(randomInt);
    }

    public static String getRandomId(List<String> ids, String diff) {
        Random r = new Random();
        String result;
        do {
            int randomInt = r.nextInt(ids.size());
            result = ids.get(randomInt);
        } while (result.equals(diff));

        return result;
    }

    public static String getRandomId(List<String> ids, List<String> diff) {
        Random r = new Random();

//        System.out.println("fushu"+ids.size());
        String result;
        if (ids.size()==0){
            return null;
        }
        do {
            int randomInt = r.nextInt(ids.size());
            result = ids.get(randomInt);
        } while (diff.contains(result));

        return result;
    }

    public static String getRandomId(List<String> ids, String diff1, List<String> diff2) {
        Random r = new Random();
        String result;
        do {
            int randomInt = r.nextInt(ids.size());
            result = ids.get(randomInt);
        } while (diff2.contains(result) || result.equals(diff1));

        return result;
    }


    public static String getRandomPics() {
        Random r = new Random();
        int max = r.nextInt(5);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < max + 1; i++) {
            result.append(faker.internet().image());
            result.append(";");
        }
        return result.toString();
    }
}
