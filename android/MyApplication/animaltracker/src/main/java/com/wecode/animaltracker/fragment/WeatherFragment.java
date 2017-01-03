package com.wecode.animaltracker.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wecode.animaltracker.R;
import com.wecode.animaltracker.activity.util.Action;
import com.wecode.animaltracker.model.Weather;
import com.wecode.animaltracker.service.WeatherDataService;
import com.wecode.animaltracker.util.Assert;
import com.wecode.animaltracker.view.WeatherDetailView;

public class WeatherFragment extends android.support.v4.app.Fragment implements Fragment {

    private WeatherDetailView weatherDetailView;

    private WeatherDataService weatherService = WeatherDataService.getInstance();

    public WeatherFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_weather_detail, container, false);
        view.findViewById(R.id.toolbar).setVisibility(View.GONE);

        Long weatherId = getArguments().getLong("weatherId");
        weatherId = weatherId == 0 ? null : weatherId;

        Action action = Action.fromString(getArguments().getString("action"));
        if (action != Action.NEW ) {
            Assert.assertNotNull("weatherId musi byt zadane", weatherId);
        }

        if (weatherId != null) {
            weatherDetailView = new WeatherDetailView(view, weatherService.find(weatherId));
        } else {
            weatherDetailView = new WeatherDetailView(view);
        }

        view.findViewById(R.id.saveWeatherButton).setVisibility(View.GONE);

        return view;
    }

    public Weather saveWeather() {
        if (weatherDetailView == null) {
            return null;
        }
        Weather weather = weatherDetailView.toWeather();
        return weatherService.save(weather);
    }

    public int getNameResourceId() {
        return R.string.weather_fragment_name;
    }


}