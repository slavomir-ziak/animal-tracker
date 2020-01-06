package com.wecode.animaltracker.activity.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.wecode.animaltracker.Globals;
import com.wecode.animaltracker.R;
import com.wecode.animaltracker.service.SampleDataService;
import com.wecode.animaltracker.service.SettingsDataService;
import com.wecode.animaltracker.util.StringUtils;

public class TransectFindingAddSampleActivity extends AppCompatActivity {

    private EditText sampleNumberInput;

    private SampleDataService sampleDataService = SampleDataService.getInstance();

    private SettingsDataService settingsDataService = SettingsDataService.getInstance();

    private Integer sampleSequenceNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transect_finding_add_sample);
        sampleNumberInput = (EditText) findViewById(R.id.sampleNumberInputId);

        checkForTrackerNameAndGenerateSampleNumber();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != RESULT_OK) {
            Toast.makeText(this, R.string.error_occured, Toast.LENGTH_SHORT).show();
            super.onActivityResult(requestCode, resultCode, data);
        }

        if (requestCode == Globals.REQ_SETTINGS) {
            checkForTrackerNameAndGenerateSampleNumber();
        }
    }

    public void addSample(View view) {

        String sampleNumber = sampleNumberInput.getText().toString();

        Intent intent = new Intent();
        intent.putExtra("sampleNumber", sampleNumber);
        intent.putExtra("sampleSequenceNumber", sampleSequenceNumber);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void checkForTrackerNameAndGenerateSampleNumber() {
        if (StringUtils.isEmpty(settingsDataService.get().getTrackerName())){
            Globals.askForTrackerName(this);
        } else {
            String[] strings = sampleDataService.generateSampleNumber(settingsDataService.get().getTrackerName());
            String sampleNumber = strings[0];
            sampleSequenceNumber = Integer.valueOf(strings[1]);
            sampleNumberInput.setText(sampleNumber);

        }
    }
}
