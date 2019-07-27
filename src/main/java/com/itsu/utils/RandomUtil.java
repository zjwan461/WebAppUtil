package com.itsu.utils;

import java.util.Random;

/**
 * @author 苏犇
 * @date 2019/7/20 10:21
 */
public class RandomUtil {

    public static void getRamdonString(int length) {
        length = Math.abs(length);
        StringBuffer sb = new StringBuffer();
        String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));
        }

        System.out.println(sb.toString());
    }

    public static void main(String[] args) {
        getRamdonString(-1);
    }
}
