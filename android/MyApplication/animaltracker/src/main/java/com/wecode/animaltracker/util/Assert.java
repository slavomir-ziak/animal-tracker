package com.wecode.animaltracker.util;

/**
 * Created by sziak on 11/2/2015.
 */
public class Assert {

    public static void assertNotNull(String message, Object notnull) {
        if (notnull == null) {
            throw new RuntimeException(message);
        }
    }

    public static void assertNotNullNotZero(String message, Long aLong) {
        assertNotNull(message, (Object)aLong);
        if (aLong == 0) {
            throw new RuntimeException("cannot be " + aLong);
        }
    }

    public static void assertNull(String message, Object notnull) {
        if (notnull != null) {
            throw new RuntimeException(message);
        }
    }

    public static void isTrue(String message, Boolean bool) {
        if (!bool) {
            throw new IllegalArgumentException(message);
        }
    }


}
