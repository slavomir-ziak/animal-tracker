package com.wecode.animaltracker.data;

import com.wecode.animaltracker.model.Persistable;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by sziak on 10/28/2015.
 */
public abstract class AbstractDataService<T extends Persistable> {

    private Long nextId = 1L;

    protected abstract Map<Long, T> getData();

    public void save(T t) {
        if (t.getId() == null) {
            Long nextId = getNextId();
            t.setId(nextId);
        }
        getData().put(t.getId(), t);
    }

    public T find(Long id) {
        return getData().get(id);

/*

        return (T) transect;*/
    }

    public List<T> list() {
        Persistable[] t = new Persistable[0];
        return (List<T>) Arrays.asList(getData().values().toArray(t));
    }

    public Long getNextId() {
        return nextId++;
    }
}
