package com.wecode.animaltracker.service;

import com.wecode.animaltracker.model.Weather;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sziak on 10/28/2015.
 */
public class WeatherDataService extends AbstractDataService<Weather> {

    private Map<Long, Weather> data = new HashMap<>();

    private static final WeatherDataService INSTANCE = new WeatherDataService();

    private WeatherDataService(){}

    public static WeatherDataService getInstance() {
        return INSTANCE;
    }

    @Override
    protected Map<Long, Weather> getData() {
        return data;
    }
}
