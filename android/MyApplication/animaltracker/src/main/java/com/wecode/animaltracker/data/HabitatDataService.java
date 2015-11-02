package com.wecode.animaltracker.data;

import com.wecode.animaltracker.model.Habitat;

/**
 * Created by sziak on 10/28/2015.
 */
public class HabitatDataService extends AbstractDataService<Habitat> {

    public static final HabitatDataService INSTANCE = new HabitatDataService();

    private HabitatDataService(){}
}
