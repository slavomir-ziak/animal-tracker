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
    /*
        private Map<Long, Transect> data = new HashMap<>();

        {
            Location startLocation = new Location("default");
            startLocation.setLongitude(15.231534534);
            startLocation.setLatitude(46.1590348);

            Transect transect = new Transect(getNextId(), 14, new Date(), startLocation, "routename1");

            transect.setEndDateTime(new Date());
            transect.setEndLocation(startLocation);

            getData().put(transect.getId(), transect);

        }

        {
            Location startLocation = new Location("default");
            startLocation.setLongitude(15.231534534);
            startLocation.setLatitude(46.1590348);

            Transect transect = new Transect(getNextId(), 3, new Date(), startLocation, "routename2");

            transect.setEndDateTime(new Date());
            transect.setEndLocation(startLocation);

            getData().put(transect.getId(), transect);

        }*/
    private static final TransectDataService INSTANCE = new TransectDataService();

    private TransectDataService(){
        super(Transect.class);
    }

    public static TransectDataService getInstance() {
        return INSTANCE;
    }

}
