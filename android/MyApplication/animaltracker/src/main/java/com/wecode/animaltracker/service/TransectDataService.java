package com.wecode.animaltracker.service;

import android.location.Location;
import com.wecode.animaltracker.model.Transect;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sziak on 10/28/2015.
 */
public class TransectDataService extends AbstractDataService<Transect> {

    private static final TransectDataService INSTANCE = new TransectDataService();

    private TransectDataService(){
        super(Transect.class);
    }

    public static TransectDataService getInstance() {
        return INSTANCE;
    }


}
