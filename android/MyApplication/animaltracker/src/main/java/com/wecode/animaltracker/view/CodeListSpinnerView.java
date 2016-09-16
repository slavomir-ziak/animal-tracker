package com.wecode.animaltracker.view;

import android.app.Activity;
import android.widget.Spinner;

import com.wecode.animaltracker.R;
import com.wecode.animaltracker.activity.util.SpinnersHelper;
import com.wecode.animaltracker.adapter.CodeListEditingAdapter;
import com.wecode.animaltracker.model.CodeList;
import com.wecode.animaltracker.service.CodeListService;

/**
 * Created by SZIAK on 9/1/2016.
 */
public class CodeListSpinnerView {

    private Spinner spinner;

    public CodeListSpinnerView(int spinnerId, String codeListName, Activity parentActivity) {
        this.spinner = (Spinner) parentActivity.findViewById(spinnerId);

        setSpinnerData(parentActivity, spinnerId, codeListName);
    }

    public void select(String value) {
        SpinnersHelper.setSelected(spinner, value);
    }

    public String getSelectedCodeListValue() {
        return ((CodeList) spinner.getSelectedItem()).getValue();
    }

    private void setSpinnerData(Activity context, int spinnerViewId, String codeListName) {

        CodeListEditingAdapter codeListEditingAdapter = new CodeListEditingAdapter(context, codeListName);

        Spinner spinnerView = (Spinner) context.findViewById(spinnerViewId);
        spinnerView.setOnItemSelectedListener(codeListEditingAdapter);
        spinnerView.setAdapter(codeListEditingAdapter);
    }
}
