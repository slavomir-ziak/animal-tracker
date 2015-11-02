package com.wecode.animaltracker.data;

import android.location.Location;
import com.wecode.animaltracker.model.Persistable;
import com.wecode.animaltracker.model.Transect;

import java.util.*;

/**
 * Created by sziak on 10/28/2015.
 */
public abstract class AbstractDataService<T extends Persistable> {

    private Long nextId = 1L;

    private Map<Long, T> data = new HashMap<>();
    {
        Location startLocation = new Location("default");
        startLocation.setLongitude(15.231534534);
        startLocation.setLatitude(46.1590348);

        Transect transect = new Transect(getNextId(), 14, new Date(), startLocation, "routename1");

        transect.setEndDateTime(new Date());
        transect.setEndLocation(startLocation);

        data.put(transect.getId(), (T) transect);

    } {
        Location startLocation = new Location("default");
        startLocation.setLongitude(15.231534534);
        startLocation.setLatitude(46.1590348);

        Transect transect = new Transect(getNextId(), 3, new Date(), startLocation, "routename2");

        transect.setEndDateTime(new Date());
        transect.setEndLocation(startLocation);

        data.put(transect.getId(), (T) transect);

    }

    public void save(T t) {
        data.put(t.getId(), t);
    }

    public T find(Long id) {
        return data.get(id);

/*

        return (T) transect;*/
    }

    public List<T> list() {
        Persistable[] t = new Persistable[0];
        return (List<T>) Arrays.asList(data.values().toArray(t));
    }

    public Long getNextId() {
        return nextId++;
    }
}
