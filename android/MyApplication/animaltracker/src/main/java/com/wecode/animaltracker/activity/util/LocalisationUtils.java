package com.wecode.animaltracker.activity.util;

import java.text.NumberFormat;
import java.text.ParseException;

/**
 * Created by slavo on 1/9/2017.
 */

public class LocalisationUtils {

    public static double parseDouble(CharSequence value) {
        String str = value.toString();
        try {
            return NumberFormat.getNumberInstance().parse(str).doubleValue();
        } catch (ParseException e) {
            // workround for Slovak locale, some keyboards (Samsung) dont show ',' on numeric keyboard,
            // so user has ability to use '.' and app will still take it
            if (str.contains(".")) {
                return parseDouble(str.replaceAll("\\.", ","));
            }
            throw new RuntimeException(e);
        }
    }

}
