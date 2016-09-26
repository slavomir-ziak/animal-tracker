package com.wecode.animaltracker.activity.util;

import android.widget.TextView;

/**
 * Created by SZIAK on 8/29/2016.
 */
public class ValidationHelper {

    public static boolean isNotEmpty(TextView ... textViews) {
        boolean valid = true;
        for (TextView textView : textViews) {
            if (textView.getText().length() == 0) {
                textView.setError("Must not by empty.");
                valid = false;
            }
        }
        return valid;
    }

    /**
     * throws exception
     *
     * @param textViews
     */
    public static void assertNotEmpty(TextView ... textViews) {
        boolean valid = isNotEmpty(textViews);
        if (!valid) {
            throw new RuntimeException();
        }
    }


    public static boolean isMaxValue(TextView textView, double maxValue) {
        double v = Double.parseDouble(textView.getText().toString());
        if (v > maxValue) {
            textView.setError("Max value is " + maxValue + ".");
            return false;
        }
        return true;
    }

    private static boolean isMinValue(TextView textView, double minValue) {
        double v = Double.parseDouble(textView.getText().toString());
        if (v < minValue) {
            textView.setError("Min value is " + minValue + ".");
            return false;
        }
        return true;
    }

    /**
     * throws Exception
     *
     * @param textView
     * @param maxValue
     */
    public static void assertMaxValue(TextView textView, double maxValue) {
        if (!isMaxValue(textView, maxValue)) {
            throw new RuntimeException();
        }
    }

    public static void assertMinValue(TextView textView, double minValue) {
        if (!isMinValue(textView, minValue)) {
            throw new RuntimeException();
        }
    }

}
