package com.example.springbootstudy.utils;

import java.util.Random;

public class RandomUtil {

    /**
     * 产生6位随机数
     *
     * @return
     */
    public static String make6IntString() {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            stringBuilder.append(random.nextInt(10));
        }
        return stringBuilder.toString();
    }
}
