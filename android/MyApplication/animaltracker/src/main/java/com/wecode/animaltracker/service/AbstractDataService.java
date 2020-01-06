package com.wecode.animaltracker.service;

import com.j256.ormlite.dao.Dao;
import com.wecode.animaltracker.model.Persistable;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Created by sziak on 10/28/2015.
 */
public abstract class AbstractDataService<T extends Persistable> {

    protected Dao<T, Long> dao;

    AbstractDataService(Dao<T, Long> dao) {
        this.dao = dao;
    }

    public T save(T t) {

        try {

            if (t.getId() == null) {
                t.setCreated(new Date());
                dao.create(t);
            } else {
                dao.update(t);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return t;
    }

    public T find(Long id) {
        try {
            return dao.queryForId(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<T> listAll() {
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAll() {
        try {
            dao.deleteBuilder().delete();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
