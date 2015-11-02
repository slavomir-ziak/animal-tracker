package com.wecode.animaltracker.activity.detail;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;
import com.wecode.animaltracker.R;
import com.wecode.animaltracker.activity.util.TextChangingSeekBarListener;

public class WeatherDetailActivity extends CommonDetailActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_detail);

        setupSeekBarChangeListeners();
    }

    private void setupSeekBarChangeListeners() {

        SeekBar sunshineSeekBar = (SeekBar) findViewById(R.id.sunshineSeekBar);
        TextView sunshineValue = (TextView) findViewById(R.id.sunshineValue);
        sunshineSeekBar.setOnSeekBarChangeListener(new TextChangingSeekBarListener(sunshineValue, "%"));

        SeekBar windSeekBar = (SeekBar) findViewById(R.id.windSeekBar);
        TextView windValue = (TextView) findViewById(R.id.windValue);
        windSeekBar.setOnSeekBarChangeListener(new TextChangingSeekBarListener(windValue, "%"));

        SeekBar rainingSeekBar = (SeekBar) findViewById(R.id.rainingSeekBar);
        TextView rainingValue = (TextView) findViewById(R.id.rainingValue);
        rainingSeekBar.setOnSeekBarChangeListener(new TextChangingSeekBarListener(rainingValue, "%"));

        SeekBar humiditySeekBar = (SeekBar) findViewById(R.id.humiditySeekBar);
        TextView humidityValue = (TextView) findViewById(R.id.humidityValue);
        humiditySeekBar.setOnSeekBarChangeListener(new TextChangingSeekBarListener(humidityValue, "%"));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_weather, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
