package com.wecode.animaltracker.activity.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.wecode.animaltracker.R;
import com.wecode.animaltracker.data.WeatherDataService;
import com.wecode.animaltracker.model.Weather;
import com.wecode.animaltracker.view.WeatherDetailView;

public class WeatherDetailActivity extends CommonDetailActivity {

    private WeatherDetailView weatherDetailView;

    private WeatherDataService weatherService = WeatherDataService.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_detail);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        extractParams(getIntent());

        if (id != null) {
            weatherDetailView = new WeatherDetailView(this, weatherService.find(id));
        } else {
            weatherDetailView = new WeatherDetailView(this);
        }

    }

    @Override
    public void onBackPressed() {
        Weather weather = weatherDetailView.toWeather();
        weatherService.save(weather);

        Intent intent = new Intent();
        intent.putExtra("id", weather.getId());
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_weather, menu);
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

    public void save(View view) {
        onBackPressed();
    }

}
