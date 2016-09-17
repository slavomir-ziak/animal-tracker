package com.wecode.animaltracker.service;

import android.util.Log;

import com.orm.SugarRecord;
import com.wecode.animaltracker.Globals;
import com.wecode.animaltracker.model.Persistable;
import com.wecode.animaltracker.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
        if (t.getId() == null) {
            t.setCreated(new Date());
        }
        Log.i(Globals.APP_NAME, "Saving " + t);
        long id = t.save();
        t.setId(id);
        return t;
    }

    public T find(Long id) {
        Assert.assertNotNull("id cannot be null", id);
        T byId = SugarRecord.findById(persistentClass, id);
        Assert.assertNotNull("entity not found by id:"+id, byId);;
        return byId;
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
