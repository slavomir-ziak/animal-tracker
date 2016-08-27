package com.wecode.animaltracker.service;

import android.util.Log;

import com.orm.SugarRecord;
import com.wecode.animaltracker.Globals;
import com.wecode.animaltracker.model.Persistable;
import com.wecode.animaltracker.util.Assert;

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

    public T save(T t) {
        Log.i(Globals.APP_NAME, "Saving " + t);
        long id = t.save();
        t.setId(id);
        return t;
    }

    public T find(Long id) {
        Assert.assertNotNull("id cannot be null", id);
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
