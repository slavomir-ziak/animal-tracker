package com.wecode.animaltracker.activity.util;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by sziak on 10/8/2015.
 */
public class SpinnersHelper {

    public static void setSpinnerData(Activity context, int spinner, int arrayResource) {

        Spinner spinnerView = (Spinner) context.findViewById(spinner);
        setSpinnerData(spinnerView, arrayResource);

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

}
