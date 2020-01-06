package com.wecode.animaltracker.service;

import com.j256.ormlite.dao.Dao;
import com.wecode.animaltracker.model.Weather;

/**
 * Created by sziak on 10/28/2015.
 */
public class WeatherDataService extends AbstractDataService<Weather> {

    private static WeatherDataService INSTANCE;

    private WeatherDataService(Dao<Weather, Long> dao) {
        super(dao);
    }

    public static WeatherDataService getInstance() {
        if (INSTANCE == null) {
            throw new IllegalStateException("INSTANCE is null, initialize first");
        }
        return INSTANCE;
    }

    public static void initialize(Dao<Weather, Long> dao) {
        WeatherDataService.INSTANCE = new WeatherDataService(dao);
    }

}
