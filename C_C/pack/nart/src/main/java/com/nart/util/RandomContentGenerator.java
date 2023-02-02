package com.nart.util;

import com.github.javafaker.Faker;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
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

    private static final Random r = new Random();

    private static Integer count = 0;

    private static final String testImgPath = "D:\\GitHub\\NART\\C_C\\pack\\nart\\src\\main\\resources\\testImg\\";

    public static String getRandomId(List<String> ids) {
        int randomInt = r.nextInt(ids.size());
        return ids.get(randomInt);
    }

    public static String getRandomId(List<String> ids, String diff) {
        String result;
        do {
            int randomInt = r.nextInt(ids.size());
            result = ids.get(randomInt);
        } while (result.equals(diff));

        return result;
    }

    public static String getRandomId(List<String> ids, List<String> diff) {
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
        String result;
        do {
            int randomInt = r.nextInt(ids.size());
            result = ids.get(randomInt);
        } while (diff2.contains(result) || result.equals(diff1));

        return result;
    }

    public static String getRandPics(AlbumType picType) {
        String result = "";
        switch (picType) {
            case USER_AVATAR: result = getRandomPics(1,1); break;
            case GROUP_AVATAR: result = getRandomPics(1,2); break;
            case STATUS_PICS: result = getRandomPics(5,0); break;
            case CHAT_PICS: result = getRandomPics(1,3);
        }
        return result;
    }

    private static String getRandomPics(int picMaxAmount, int albumNumber) {
        int max = 0;
        StringBuilder result = new StringBuilder();

        if (picMaxAmount > 1) {
            max = r.nextInt(picMaxAmount);
            for (int i = 0; i < max + 1; i++) {
                String picUrl = getRandomPic(albumNumber);
                while (picUrl.equals("") || picUrl.equals("repeat")) {
                    picUrl = getRandomPic(albumNumber);
                }
                result.append(picUrl);
                result.append(";");
            }
        } else {
            String picUrl = getRandomPic(albumNumber);
            while (picUrl.equals("") || picUrl.equals("repeat")) {
                picUrl = getRandomPic(albumNumber);
            }
            result.append(picUrl);
        }

        return result.toString();
    }

    private static String getRandomPic(int albumNumber) {
        int i = count++;
        String imgName = "https://picsum.photos/400/400?random=" + i;
        return imgName;
//        String imgPath = testImgPath + imgName;
//        File file = new File(imgPath);
//        byte[] bytes = new byte[0];
//        try {
//            bytes = fileToByte(file);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        if (bytes.length == 0) return "";
//
//        String suffix = StringUtils.substringAfterLast(imgName, ".");
//        String fileName = UUID.randomUUID().toString() + "." + suffix;
//        String url = "";
//        try {
//            url = ImgtuUtil.uploadPic(bytes, fileName, albumNumber);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        if(StringUtils.isNotBlank(url)) {
//            if(url.equals("400")) {
//                return "repeat";
//            }
//            return url;
//        }
//        return "";
    }

    private static byte[] fileToByte(File img) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] bytes = new byte[0];
        try {
            BufferedImage bi;
            bi = ImageIO.read(img);
            ImageIO.write(bi, "jpg", baos);
            bytes = baos.toByteArray();
            System.err.println(bytes.length);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            baos.close();
        }
        return bytes;
    }

}
