package com.wecode.animaltracker.service;

import com.wecode.animaltracker.model.Habitat;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sziak on 10/28/2015.
 */
public class HabitatDataService extends AbstractDataService<Habitat> {

    private Map<Long, Habitat> data = new HashMap<>();

    private static final HabitatDataService INSTANCE = new HabitatDataService();

    private HabitatDataService(){}

    public static HabitatDataService getInstance() {
        return INSTANCE;
    }

    @Override
    protected Map<Long, Habitat> getData() {
        return data;
    }
}
