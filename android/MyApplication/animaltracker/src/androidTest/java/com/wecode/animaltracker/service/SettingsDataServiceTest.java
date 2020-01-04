package com.wecode.animaltracker.service;

import android.support.annotation.NonNull;
import android.support.test.runner.AndroidJUnit4;

import com.wecode.animaltracker.model.Settings;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(AndroidJUnit4.class)
public class SettingsDataServiceTest {

    SettingsDataService service = SettingsDataService.getInstance();

    @Before
    public void setUp() {
        service.deleteAll();
    }

    @Test
    public void save() {
        Settings settings = saveSettings();
        assertThat(settings.getId(), notNullValue());
    }

    @NonNull
    private Settings saveSettings() {
        Settings settings = new Settings();
        settings.setLocationFormat("test");
        settings.setTrackerName("test");
        settings.setComment("test");
        service.save(settings);
        return settings;
    }

    @Test
    public void find() {
        Settings settings = saveSettings();
        Settings sameSettings = service.find(settings.getId());

        assertThat(settings, equalTo(sameSettings));
    }

    @Test
    public void listAll() {
        saveSettings();
        saveSettings();
        saveSettings();

        assertThat(service.listAll().size(), is(3));
    }

    @Test
    public void deleteAll() {

        saveSettings();
        saveSettings();
        saveSettings();

        assertThat(service.listAll().size(), is(3));

        service.deleteAll();

        assertThat(service.listAll().size(), is(0));

    }

    @Test
    public void get() {
        Settings settings = saveSettings();
        Settings sameSettings = service.get();

        assertThat(settings, equalTo(sameSettings));
    }
}