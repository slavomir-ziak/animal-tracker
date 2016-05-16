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

    {
        Photo photo = new Photo(getNextId(), 1L, "cdb3bfc8-9ede-4fda-909b-61ac7dc17606.jpg", "");
        data.put(photo.getId(), photo);

        photo = new Photo(getNextId(), 1L, "e96e5ed4-0fb7-45ee-9907-bd6789f74dcf.jpg", "");
        data.put(photo.getId(), photo);

        photo = new Photo(getNextId(), 1L, "f3549868-8397-434a-8aa7-904a751c65c4.jpg", "");
        data.put(photo.getId(), photo);
        
        photo = new Photo(getNextId(), 2L, "e96e5ed4-0fb7-45ee-9907-bd6789f74dcf.jpg", "");
        data.put(photo.getId(), photo);

        photo = new Photo(getNextId(), 2L, "f3549868-8397-434a-8aa7-904a751c65c4.jpg", "");
        data.put(photo.getId(), photo);
        
    }

    private PhotosDataService() {
    }

    public static PhotosDataService getInstance() {
        return INSTANCE;
    }

    @Override
    protected Map<Long, Photo> getData() {
        return data;
    }

    public List<Photo> getPhotosForTransect(Long transectDetailId) {
        List<Photo> results = new ArrayList<>();

        for (Photo photo : data.values()) {
            if (photo.getTransectFindingId().equals(transectDetailId)) {
                results.add(photo);
            }
        }
        return results;
    }
}
