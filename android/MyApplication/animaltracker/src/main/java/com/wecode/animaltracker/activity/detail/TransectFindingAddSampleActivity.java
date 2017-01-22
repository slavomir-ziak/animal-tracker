package com.wecode.animaltracker.activity.detail;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.wecode.animaltracker.R;

public class TransectFindingAddSampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transect_finding_add_sample);

    }

    public void addSample(View view) {
        EditText sampleNumberInput = (EditText) findViewById(R.id.sampleNumberInputId);

        String sampleNumber = sampleNumberInput.getText().toString();

        Intent intent = new Intent();
        intent.putExtra("sampleNumber", sampleNumber);
        setResult(RESULT_OK, intent);
        finish();
    }
}
