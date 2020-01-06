package com.wecode.animaltracker.service;

import com.j256.ormlite.dao.Dao;
import com.wecode.animaltracker.model.EntityName;
import com.wecode.animaltracker.model.Photo;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by sziak on 16-May-16.
 */
public class PhotoDataService extends AbstractDataService<Photo> {

    private static PhotoDataService INSTANCE;

    private PhotoDataService(Dao<Photo, Long> dao) {
        super(dao);
    }

    public static PhotoDataService getInstance() {
        if (INSTANCE == null) {
            throw new IllegalStateException("INSTANCE is null, initialize first");
        }
        return INSTANCE;
    }

    public static void initialize(Dao<Photo, Long> dao) {
        PhotoDataService.INSTANCE = new PhotoDataService(dao);
    }

    public List<Photo> findByEntityIdAndName(Long entityId, String entityName) {
        try {
            return dao.queryBuilder().where().eq(Photo.ENTITY_NAME_COLUMN, EntityName.valueOf(entityName))
                    .and().eq(Photo.ENTITY_ID_COLUMN, entityId).query();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
