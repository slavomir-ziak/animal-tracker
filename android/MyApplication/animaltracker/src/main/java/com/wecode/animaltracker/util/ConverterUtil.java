package com.wecode.animaltracker.util;

/**
 * Created by sziak on 08-May-16.
 */
public class ConverterUtil {

    public static Integer toInteger(String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        return Integer.valueOf(string);
    }

    public static String toString(CharSequence value) {
        if (value == null) {
            return null;
        }

        return value.toString();
    }

}
