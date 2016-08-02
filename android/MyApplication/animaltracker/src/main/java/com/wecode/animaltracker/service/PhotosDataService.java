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

    /*{
        new Photo(null, 1L, "cdb3bfc8-9ede-4fda-909b-61ac7dc17606.jpg", "").save();
        new Photo(null, 1L, "e96e5ed4-0fb7-45ee-9907-bd6789f74dcf.jpg", "").save();
        new Photo(null, 1L, "f3549868-8397-434a-8aa7-904a751c65c4.jpg", "").save();
        new Photo(null, 2L, "e96e5ed4-0fb7-45ee-9907-bd6789f74dcf.jpg", "").save();
        new Photo(null, 2L, "f3549868-8397-434a-8aa7-904a751c65c4.jpg", "").save();
    }*/

    private PhotosDataService() {
        super(Photo.class);
    }

    public static PhotosDataService getInstance() {
        return INSTANCE;
    }

    public List<Photo> getPhotosForTransect(Long transectDetailId) {
        return Photo.findWithQuery(Photo.class, "transect_finding_id = ?", transectDetailId.toString());
    }
}
