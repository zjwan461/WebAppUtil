package com.itsu.utils;

import java.util.Random;

/**
 * @author 苏犇
 * @date 2019/7/20 10:21
 */
public class RandomUtil {

    private static String ALL_CHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String getRamdonString(int length) {
        length = Math.abs(length);
        StringBuffer sb = new StringBuffer();

        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(ALL_CHAR.charAt(random.nextInt(ALL_CHAR.length())));
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        getRamdonString(-1);
    }
}
