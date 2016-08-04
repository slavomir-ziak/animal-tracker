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

    private Map<Long, Photo> data = new HashMap<>();

    private static final PhotosDataService INSTANCE = new PhotosDataService();

    private PhotosDataService() {
        super(Photo.class);
    }

    public static PhotosDataService getInstance() {
        return INSTANCE;
    }

    public List<Photo> getPhotosForTransect(Long transectId) {
        return Photo.findWithQuery(Photo.class, "transect_finding_id = ?", transectId.toString());
    }
}
