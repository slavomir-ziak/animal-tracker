package com.wecode.animaltracker.activity.util;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.wecode.animaltracker.Globals;
import com.wecode.animaltracker.activity.adapter.CodeListEditingAdapter;
import com.wecode.animaltracker.model.CodeList;

/**
 * Created by sziak on 10/8/2015.
 */
public class SpinnersHelper {

    public static void setSpinnerData(Activity context, int spinnerViewId, String codeListName) {

        CodeListEditingAdapter codeListEditingAdapter = new CodeListEditingAdapter(context, codeListName);

        Spinner spinnerView = (Spinner) context.findViewById(spinnerViewId);
        spinnerView.setOnItemSelectedListener(codeListEditingAdapter);
        spinnerView.setAdapter(codeListEditingAdapter);
    }


    public static void setSpinnerData(Spinner spinnerView, int arrayResource) {
        if (spinnerView == null) {
            throw new RuntimeException("spinnerView is null");
        }

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(spinnerView.getContext(),
                arrayResource, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerView.setAdapter(adapter);
    }

    @SuppressWarnings("unchecked")
    public static void setSelected(final Spinner spinner, String value) {
        if (value == null) {
            Log.w(Globals.APP_NAME, spinner + " null value");
            return;
        }

        if (spinner.getAdapter() instanceof ArrayAdapter) {
            int position = ((ArrayAdapter<String>) spinner.getAdapter()).getPosition(value);
            spinner.setSelection(position, true);
        } else if (spinner.getAdapter() instanceof CodeListEditingAdapter) {
            final int position = ((CodeListEditingAdapter) spinner.getAdapter()).getPosition(value);
            Log.i(Globals.APP_NAME, value + " has position " + position);

            spinner.post(new Runnable() {
                public void run() {
                    spinner.setSelection(position, true);
                }
            });

        } else {
            throw new RuntimeException("cannot handle " + spinner.getAdapter());
        }

    }


}
