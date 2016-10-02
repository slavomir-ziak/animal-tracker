package com.wecode.animaltracker.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wecode.animaltracker.R;
import com.wecode.animaltracker.activity.util.Action;
import com.wecode.animaltracker.model.Persistable;
import com.wecode.animaltracker.model.Weather;
import com.wecode.animaltracker.service.WeatherDataService;
import com.wecode.animaltracker.util.Assert;
import com.wecode.animaltracker.view.WeatherDetailView;

public class WeatherFragment extends Fragment implements ITransect {

    private WeatherDetailView weatherDetailView;

    private WeatherDataService weatherService = WeatherDataService.getInstance();

    public WeatherFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_weather_detail, container, false);
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
        Weather weather = weatherDetailView.toWeather();
        return weatherService.save(weather);
    }

    @Override
    public Persistable getData() {
        return saveWeather();
    }

    public String getName() {
        return "Weather";
    }


}