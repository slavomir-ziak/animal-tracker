package com.wecode.animaltracker.view;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.wecode.animaltracker.activity.util.SpinnersHelper;
import com.wecode.animaltracker.adapter.CodeListEditingAdapter;
import com.wecode.animaltracker.model.CodeList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SZIAK on 9/1/2016.
 */
public class CodeListSpinnerView implements AdapterView.OnItemSelectedListener {

    private Spinner spinner;

    private boolean enableEmptyValue = true;

    List<AdapterView.OnItemSelectedListener> listeners = new ArrayList<>();

    public CodeListSpinnerView(int spinnerId, String codeListName, Activity parentActivity) {
        this.spinner = (Spinner) parentActivity.findViewById(spinnerId);

        setSpinnerData(parentActivity, codeListName, spinner);
    }

    public CodeListSpinnerView(int spinnerId, String codeListName, Activity parentActivity, boolean enableEmptyValue, String defaultValue) {
        this.enableEmptyValue = enableEmptyValue;
        this.spinner = (Spinner) parentActivity.findViewById(spinnerId);

        setSpinnerData(parentActivity, codeListName, spinner, defaultValue);
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

    private void setSpinnerData(Activity context, String codeListName, Spinner spinnerView, String defaultValue) {

        CodeListEditingAdapter codeListEditingAdapter = new CodeListEditingAdapter(context, codeListName, enableEmptyValue, defaultValue);
        listeners.add(codeListEditingAdapter);
        spinnerView.setOnItemSelectedListener(this);
        spinnerView.setAdapter(codeListEditingAdapter);
    }

    private void setSpinnerData(Activity context, String codeListName, Spinner spinnerView) {
        setSpinnerData(context, codeListName, spinnerView, null);
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

    public void addOnItemSelectedListener(AdapterView.OnItemSelectedListener onItemSelectedListener) {
        listeners.add(onItemSelectedListener);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        for (AdapterView.OnItemSelectedListener listener : listeners) {
            listener.onItemSelected(parent, view, position, id);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        for (AdapterView.OnItemSelectedListener listener : listeners) {
            listener.onNothingSelected(parent);
        }
    }
}
