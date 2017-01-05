package com.wecode.animaltracker.activity.util;

import android.app.Activity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.wecode.animaltracker.Globals;
import com.wecode.animaltracker.R;
import com.wecode.animaltracker.adapter.CodeListEditingAdapter;

/**
 * Created by sziak on 10/8/2015.
 */
public class SpinnersHelper {

    public static void setSpinnerData(Spinner spinnerView, int arrayResource) {
        if (spinnerView == null) {
            throw new RuntimeException("spinnerView is null");
        }

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(spinnerView.getContext(),
                arrayResource, R.layout.spinner_dropdown_item);

        spinnerView.setAdapter(adapter);
    }

    @SuppressWarnings("unchecked")
    public static void setSelected(final Spinner spinner, String value) {
        if (value == null) {
            return;
        }

        if (spinner.getAdapter() instanceof ArrayAdapter) {
            int position = ((ArrayAdapter<String>) spinner.getAdapter()).getPosition(value);
            spinner.setSelection(position, true);
        } else if (spinner.getAdapter() instanceof CodeListEditingAdapter) {
            final int position = ((CodeListEditingAdapter) spinner.getAdapter()).getPosition(value, false);
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
