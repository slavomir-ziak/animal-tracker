package com.wecode.animaltracker.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.orm.util.ManifestHelper;
import com.wecode.animaltracker.R;
import com.wecode.animaltracker.model.Settings;
import com.wecode.animaltracker.service.SettingsDataService;

/**
 * Created by SZIAK on 9/25/2016.
 */
public class SettingsActivity extends AppCompatActivity {

    private SettingsDataService service = SettingsDataService.getInstance();

    private TextView trackerName;

    private RadioButton settingsLocationDecimal;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();

        int databaseVersion = ManifestHelper.getDatabaseVersion(this);
        TextView versionTextView = (TextView) findViewById(R.id.versionText);
        versionTextView.setText("0.0."+databaseVersion);

    }

    private void init() {
        Settings settings = service.get();

        trackerName = (TextView) findViewById(R.id.settingsTrackerName);
        settingsLocationDecimal = (RadioButton) findViewById(R.id.settingsLocationDecimal);
        RadioButton settingsLocationDMS = (RadioButton) findViewById(R.id.settingsLocationDMS);

        trackerName.setText(settings.getTrackerName() != null ? settings.getTrackerName() : "");
        settingsLocationDecimal.setChecked(settings.isLocationDecimal());
        settingsLocationDMS.setChecked(settings.isLocationDMS());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            saveSettings();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveSettings() {

        Settings settings = service.get();
        String locationFormat = settingsLocationDecimal.isChecked() ? Settings.LOCATION_DECIMAL : Settings.LOCATION_DMS;
        settings.setLocationFormat(locationFormat);
        settings.setTrackerName(trackerName.getText().toString());
        service.save(settings);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }
}
