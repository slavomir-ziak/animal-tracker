package com.wecode.animaltracker.activity.util;

import android.content.Context;
import android.widget.TextView;

import com.wecode.animaltracker.R;

/**
 * Created by SZIAK on 8/29/2016.
 */
public class ValidationHelper {

    public static boolean isNotEmpty(Context context, TextView ... textViews) {
        boolean valid = true;
        for (TextView textView : textViews) {
            if (textView.getText().length() == 0) {
                textView.setError(context.getString(R.string.validation_must_not_be_empty));
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
    public static void assertNotEmpty(Context context, TextView ... textViews) {
        boolean valid = isNotEmpty(context, textViews);
        if (!valid) {
            throw new RuntimeException();
        }
    }


    public static boolean isMaxValue(Context context, TextView textView, double maxValue) {
        double v = Double.parseDouble(textView.getText().toString());
        if (v > maxValue) {
            textView.setError(context.getString(R.string.validation_max_value, maxValue));
            return false;
        }
        return true;
    }

    private static boolean isMinValue(Context context, TextView textView, double minValue) {
        double v = Double.parseDouble(textView.getText().toString());
        if (v < minValue) {
            textView.setError(context.getString(R.string.validation_min_value, minValue));
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
    public static void assertMaxValue(Context context, TextView textView, double maxValue) {
        if (!isMaxValue(context, textView, maxValue)) {
            throw new RuntimeException();
        }
    }

    public static void assertMinValue(Context context, TextView textView, double minValue) {
        if (!isMinValue(context, textView, minValue)) {
            throw new RuntimeException();
        }
    }

}
