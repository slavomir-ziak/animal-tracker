package com.wecode.animaltracker.service;

import com.j256.ormlite.dao.Dao;
import com.wecode.animaltracker.model.Transect;

/**
 * Created by sziak on 10/28/2015.
 */
public class TransectDataService extends AbstractDataService<Transect> {

    private static TransectDataService INSTANCE;

    private TransectDataService(Dao<Transect, Long> dao) {
        super(dao);
    }

    public static TransectDataService getInstance() {
        if (INSTANCE == null) {
            throw new IllegalStateException("INSTANCE is null, initialize first");
        }
        return INSTANCE;
    }

    public static void initialize(Dao<Transect, Long> dao) {
        TransectDataService.INSTANCE = new TransectDataService(dao);
    }


}
