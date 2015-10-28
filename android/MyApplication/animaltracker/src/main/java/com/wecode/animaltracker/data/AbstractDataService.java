package com.wecode.animaltracker.data;

import java.util.Collection;
import java.util.Map;

/**
 * Created by sziak on 10/28/2015.
 */
public abstract class AbstractDataService<T> {

    private Long nextId = 1L;

    private Map<Long, T> data;

    public Long save(T t) {
        data.put(nextId++, t);
        return nextId;
    }

    public T find(Long id) {
        return data.get(id);
    }

    public Collection<T> list() {
        return data.values();
    }

}
