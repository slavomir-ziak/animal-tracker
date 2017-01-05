package com.wecode.animaltracker.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.wecode.animaltracker.Globals;
import com.wecode.animaltracker.R;
import com.wecode.animaltracker.model.CodeList;
import com.wecode.animaltracker.service.CodeListService;

import java.util.List;
import java.util.Stack;

/**
 * Created by SZIAK on 8/31/2016.
 */
public class CodeListEditingAdapter extends BaseAdapter implements AdapterView.OnItemSelectedListener {

    private static final Long NEW_ITEM_ID = -1L;
    private static final Long EMPTY_ITEM_ID = -2L;

    private static final String EMPTY_ITEM_TEXT = "";
    private static final String NEW_ITEM_TEXT = "New...";
    private static final String NEW_ITEM_TEXT_SK = "Nový...";

    private Activity context;

    private String codeListName;
    private boolean enableEmptyValue;

    private CodeListService codeListService = CodeListService.getInstance();

    private List<CodeList> codeList;

    private Stack<Integer> previousSelected = new Stack<>();

    private String defaultValue;

    public CodeListEditingAdapter(Activity context, String codeListName, boolean enableEmptyValue) {
        this.context = context;
        this.codeListName = codeListName;
        this.enableEmptyValue = enableEmptyValue;

        reloadCodeListValues();
    }

    public CodeListEditingAdapter(Activity context, String codeListName, boolean enableEmptyValue, String defaultValue) {
        this.context = context;
        this.codeListName = codeListName;
        this.enableEmptyValue = enableEmptyValue;
        this.defaultValue = defaultValue;

        reloadCodeListValues();
    }

    private void reloadCodeListValues() {
        codeList = codeListService.findByName(codeListName);
        if (enableEmptyValue) {
            codeList.add(0, new CodeList(EMPTY_ITEM_ID, EMPTY_ITEM_TEXT, EMPTY_ITEM_TEXT));
        }

        if (defaultValue != null) {
            setDefaultValueToFirstPlace();
        }

        codeList.add(new CodeList(NEW_ITEM_ID, NEW_ITEM_TEXT, NEW_ITEM_TEXT_SK));
    }

    private void setDefaultValueToFirstPlace() {
        int defaultIndex = codeList.indexOf(codeListService.findByNameAndLocalisedValue(codeListName, defaultValue));
        if (defaultIndex >= 0) {
            CodeList defaultValue = codeList.get(defaultIndex);
            codeList.remove(defaultIndex);
            codeList.add(0, defaultValue);
        } else {
            throw new RuntimeException(defaultValue + " not present in codelist named: " + codeListName);
        }

    }

    @Override
    public int getCount() {
        return codeList.size();
    }

    @Override
    public Object getItem(int i) {
        return codeList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return codeList.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = context.getLayoutInflater();

        TextView textView;
        if (view != null) {
            textView = (TextView) view.findViewById(R.id.text1);
        }  else {
            textView = (TextView) inflater.inflate(R.layout.spinner_dropdown_item, viewGroup, false);
        }

        textView.setText(codeList.get(i).getLocalisedValue());

        return textView;
    }

    @Override
    public void onItemSelected(final AdapterView<?> adapterView, View view, final int position, long id) {

        previousSelected.push(position);

        if (id != NEW_ITEM_ID) {
            return;
        }

        final EditText textInput = new EditText(context);

        new AlertDialog.Builder(context)
                .setTitle(R.string.new_item)
                .setMessage(R.string.new_item_type_name)
                .setView(textInput)
                .setPositiveButton("OK", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int whichButton) {

                        String newCodeListItem = textInput.getText().toString();

                        CodeList byNameAndValue = codeListService.findByNameAndLocalisedValue(codeListName, newCodeListItem);
                        if (byNameAndValue == null) {
                            codeListService.save(new CodeList(codeListName, newCodeListItem, newCodeListItem, null, CodeList.SOURCE_USER));
                            reloadCodeListValues();
                        }

                        int position1 = getPosition(newCodeListItem, true);
                        setSelected((Spinner) adapterView, position1);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        previousSelected.pop();
                        String value = codeList.get(previousSelected.peek()).getLocalisedValue();

                        int position1 = getPosition(value, true);
                        setSelected((Spinner) adapterView, position1);
                    }
                })
                .show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        System.out.println("Nothing selected");
    }

    public int getPosition(String value, boolean localised) {
        for (int i = 0; i < codeList.size(); i++) {
            String valueToCompare = localised ? codeList.get(i).getLocalisedValue() : codeList.get(i).getValue();
            if (value.equals(valueToCompare)) {
                return i;
            }
        }
        Log.e(Globals.APP_NAME, "Codelist value not present in DB! value="+value+", localised="+localised);
        throw new RuntimeException("Codelist value not present in DB! value="+value+", localised="+localised);
    }


    private static void setSelected(final Spinner spinner, final int position) {

        // this hack is here so spinner correctly shows selected value
        spinner.postDelayed(new Runnable() {
            public void run() {
                spinner.setSelection(position, true);
            }
        }, 300);
    }
}
