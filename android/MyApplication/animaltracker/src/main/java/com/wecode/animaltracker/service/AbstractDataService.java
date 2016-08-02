package com.wecode.animaltracker.service;

import android.util.Log;

import com.orm.SugarRecord;
import com.wecode.animaltracker.model.Persistable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by sziak on 10/28/2015.
 */
public abstract class AbstractDataService<T extends Persistable> {

    private Class<T> persistentClass;

    protected AbstractDataService(Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }

    public void save(T t) {
        Log.i(getClass().getSimpleName(), "Saving " + t);
        t.save();
    }

    public T find(Long id) {
        return SugarRecord.findById(persistentClass, id);
    }

    public List<T> list() {
        List<T> result = new ArrayList<>();
        Iterator<T> all = SugarRecord.findAll(persistentClass);
        while(all.hasNext()) {
            result.add(all.next());
        }
        return result;
    }

}
