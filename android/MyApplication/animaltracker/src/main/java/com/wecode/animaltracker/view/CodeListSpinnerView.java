package com.wecode.animaltracker.view;

import android.app.Activity;
import android.view.View;
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

    private boolean enableEmptyValue = true;

    public CodeListSpinnerView(int spinnerId, String codeListName, Activity parentActivity) {
        this.spinner = (Spinner) parentActivity.findViewById(spinnerId);

        setSpinnerData(parentActivity, codeListName, spinner);
    }

    public CodeListSpinnerView(int spinnerId, String codeListName, Activity parentActivity, View view) {
        this.spinner = (Spinner) view.findViewById(spinnerId);

        setSpinnerData(parentActivity, codeListName, spinner);
    }

    public CodeListSpinnerView(int spinnerId, String codeListName, Activity parentActivity, View view, boolean enableEmptyValue) {
        this.enableEmptyValue = enableEmptyValue;
        this.spinner = (Spinner) view.findViewById(spinnerId);
        setSpinnerData(parentActivity, codeListName, spinner);
    }

    public void select(String value) {
        SpinnersHelper.setSelected(spinner, value);
    }

    public String getSelectedCodeListValue() {
        return ((CodeList) spinner.getSelectedItem()).getValue();
    }

    private void setSpinnerData(Activity context, String codeListName, Spinner spinnerView ) {

        CodeListEditingAdapter codeListEditingAdapter = new CodeListEditingAdapter(context, codeListName, enableEmptyValue);

        spinnerView.setOnItemSelectedListener(codeListEditingAdapter);
        spinnerView.setAdapter(codeListEditingAdapter);
    }

    public Spinner getSpinner() {
        return spinner;
    }

    public boolean isEnableEmptyValue() {
        return enableEmptyValue;
    }

    public void setEnableEmptyValue(boolean enableEmptyValue) {
        this.enableEmptyValue = enableEmptyValue;
    }
}
