package com.wecode.animaltracker.service;

import com.j256.ormlite.dao.Dao;
import com.wecode.animaltracker.model.Settings;

/**
 * Created by SZIAK on 9/25/2016.
 */
public class SettingsDataService extends AbstractDataService<Settings> {

    private static SettingsDataService INSTANCE;

    private SettingsDataService(Dao<Settings, Long> dao) {
        super(dao);
    }

    public static SettingsDataService getInstance() {
        if (INSTANCE == null) {
            throw new IllegalStateException("INSTANCE is null, initialize first");
        }
        return INSTANCE;
    }

    public static void initialize(Dao<Settings, Long> dao) {
        SettingsDataService.INSTANCE = new SettingsDataService(dao);
    }
    
    public Settings get() {
        return listAll().get(0);
    }
}
