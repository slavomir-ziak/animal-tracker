package com.wecode.animaltracker.service;

import com.wecode.animaltracker.model.Habitat;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sziak on 10/28/2015.
 */
public class HabitatDataService extends AbstractDataService<Habitat> {

    private static final HabitatDataService INSTANCE = new HabitatDataService();

    private HabitatDataService(){
        super(Habitat.class);
    }

    public static HabitatDataService getInstance() {
        return INSTANCE;
    }

}
