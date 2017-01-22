package com.wecode.animaltracker.util;

/**
 * Created by SZIAK on 9/20/2016.
 */
public class StringUtils {

    public static boolean isNotEmpty(String s) {
        return s != null && s.length() > 0;
    }

    public static boolean isEmpty(String s) {
        return s == null || s.length() == 0;
    }
}
