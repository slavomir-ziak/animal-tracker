package com.wecode.animaltracker.service;

import com.wecode.animaltracker.model.Photo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sziak on 16-May-16.
 */
public class PhotosDataService extends AbstractDataService<Photo> {

    private static final PhotosDataService INSTANCE = new PhotosDataService();

    private PhotosDataService() {
        super(Photo.class);
    }

    public static PhotosDataService getInstance() {
        return INSTANCE;
    }

    public List<Photo> findByEntityIdAndName(Long entityId, String entityName) {
        return Photo.find(Photo.class, "ENTITY_NAME = ? AND ENTITY_ID = ?", entityName, entityId.toString());
    }
}
