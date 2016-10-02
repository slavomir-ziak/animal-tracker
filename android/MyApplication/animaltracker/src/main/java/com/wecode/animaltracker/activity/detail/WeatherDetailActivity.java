package com.wecode.animaltracker.activity.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.wecode.animaltracker.R;
import com.wecode.animaltracker.model.Photo;
import com.wecode.animaltracker.service.WeatherDataService;
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
            weatherDetailView = new WeatherDetailView(findViewById(android.R.id.content), weatherService.find(id));
        } else {
            weatherDetailView = new WeatherDetailView(findViewById(android.R.id.content));
        }

        entityName = Photo.EntityName.WEATHER;
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
        getMenuInflater().inflate(R.menu.menu_weather, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void save(View view) {
        onBackPressed();
    }

}
