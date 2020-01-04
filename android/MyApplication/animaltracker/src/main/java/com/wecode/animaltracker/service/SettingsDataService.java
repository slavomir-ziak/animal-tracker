package com.wecode.animaltracker.service;

import com.wecode.animaltracker.model.Settings;

/**
 * Created by SZIAK on 9/25/2016.
 */
public class SettingsDataService extends AbstractDataService<Settings> {

    private static final SettingsDataService INSTANCE = new SettingsDataService();

    protected SettingsDataService() {
        super(Settings.class);
    }

    public static SettingsDataService getInstance() {
        return INSTANCE;
    }

    public Settings get() {
        return listAll().get(0);
    }
}
