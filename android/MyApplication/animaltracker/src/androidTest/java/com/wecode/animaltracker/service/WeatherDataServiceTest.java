package com.wecode.animaltracker.service;

import android.support.annotation.NonNull;
import android.support.test.runner.AndroidJUnit4;

import com.wecode.animaltracker.model.Weather;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class WeatherDataServiceTest {

    WeatherDataService service = WeatherDataService.getInstance();

    @Before
    public void setUp() {
        service.deleteAll();
    }

    @Test
    public void save() {
        Weather weather = saveWeather();

        assertThat(weather.getId(), notNullValue());
    }

    @NonNull
    private Weather saveWeather() {
        Weather weather = new Weather();
        weather.setHumidity(1);
        weather.setComment("test");
        service.save(weather);
        return weather;
    }

    @Test
    public void find() {
        Weather weather = saveWeather();
        Weather sameWeather = service.find(weather.getId());
        assertThat(weather, equalTo(sameWeather));
    }

    @Test
    public void listAll() {
        saveWeather();
        saveWeather();
        saveWeather();
        assertThat(service.listAll().size(), is(3));
    }

    @Test
    public void deleteAll() {
        saveWeather();
        saveWeather();
        saveWeather();
        assertThat(service.listAll().size(), is(3));
        service.deleteAll();

        assertThat(service.listAll().size(), is(0));
    }
}