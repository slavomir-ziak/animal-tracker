package com.wecode.animaltracker.activity.util;


import android.content.Context;
import android.widget.TextView;

import com.wecode.animaltracker.R;

import java.text.NumberFormat;
import java.text.ParseException;


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

    private static boolean isDouble(Context context, TextView ... textViews) {
        boolean valid = true;
        for (TextView textView : textViews) {
            String text = textView.getText().toString();

            try {
                LocalisationUtils.parseDouble(text);

            } catch (Exception ex) {
                if (ex instanceof ParseException || ex.getCause() instanceof ParseException) {
                    textView.setError(context.getString(R.string.validation_must_be_number));
                    valid = false;
                    continue;
                }
                throw ex;
            }
        }
        return valid;
    }
    /**
     * throws exception
     *
     * @param textViews
     */
    public static void assertNotEmpty(Context context, TextView ... textViews) throws ValidationException {
        boolean valid = isNotEmpty(context, textViews);
        if (!valid) {
            throw new ValidationException("must not be empty");
        }
    }

    public static void assertDouble(Context context, TextView ... textViews) throws ValidationException {
        boolean valid = isDouble(context, textViews);
        if (!valid) {
            throw new ValidationException("must be number");
        }
    }

    public static boolean isMaxValue(Context context, TextView textView, double maxValue) {

        Number value = LocalisationUtils.parseDouble(textView.getText().toString());
        if (value.doubleValue() > maxValue) {
            textView.setError(context.getString(R.string.validation_max_value, maxValue));
            return false;
        }

        return true;
    }

    private static boolean isMinValue(Context context, TextView textView, double minValue) {
        double value = LocalisationUtils.parseDouble(textView.getText().toString());
        if (value < minValue) {
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
    public static void assertMaxValue(Context context, TextView textView, double maxValue) throws ValidationException {
        if (!isMaxValue(context, textView, maxValue)) {
            throw new ValidationException("max value has to be " + maxValue);
        }
    }

    public static void assertMinValue(Context context, TextView textView, double minValue) throws ValidationException {
        if (!isMinValue(context, textView, minValue)) {
            throw new ValidationException("min value has to be " + minValue);
        }
    }

}
