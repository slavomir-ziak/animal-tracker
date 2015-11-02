package com.wecode.animaltracker.util;

/**
 * Created by sziak on 11/2/2015.
 */
public class Assert {

    public static void notNull(String message, Object notnull) {
        if (notnull == null) {
            throw new NullPointerException(message);
        }
    }

    public static void isTrue(String message, Boolean bool) {
        if (!bool) {
            throw new IllegalArgumentException(message);
        }
    }


}
