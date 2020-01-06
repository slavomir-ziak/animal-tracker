package com.wecode.animaltracker.service;

import com.j256.ormlite.dao.Dao;
import com.wecode.animaltracker.model.Habitat;

/**
 * Created by sziak on 10/28/2015.
 */
public class HabitatDataService extends AbstractDataService<Habitat> {

    private static HabitatDataService INSTANCE;

    private HabitatDataService(Dao<Habitat, Long> dao) {
        super(dao);
    }

    public static HabitatDataService getInstance() {
        if (INSTANCE == null) {
            throw new IllegalStateException("INSTANCE is null, initialize first");
        }
        return INSTANCE;
    }

    public static void initialize(Dao<Habitat, Long> dao) {
        HabitatDataService.INSTANCE = new HabitatDataService(dao);
    }


}
