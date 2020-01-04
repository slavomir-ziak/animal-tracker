package com.wecode.animaltracker.service;

import android.support.test.runner.AndroidJUnit4;

import com.wecode.animaltracker.model.EntityName;
import com.wecode.animaltracker.model.Photo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class PhotosDataServiceTest {

    PhotosDataService service = PhotosDataService.getInstance();

    @Before
    public void setUp() throws Exception {
        service.deleteAll();
    }

    @Test
    public void testFindByEntityIdAndName() {

        Photo testfilename = service.save(new Photo(EntityName.TRANECT_FINDING_FOOTPRINT, 1L, "testfilename"));
        assertThat(testfilename.getId(), notNullValue());

        testfilename = service.save(new Photo(EntityName.TRANECT_FINDING_FECES, 1L, "testfilename1"));
        assertThat(testfilename.getId(), notNullValue());
        testfilename = service.save(new Photo(EntityName.TRANECT_FINDING_FECES, 1L, "testfilename1"));

        assertThat(testfilename.getId(), notNullValue());

        assertThat(service.findByEntityIdAndName(1L, EntityName.TRANECT_FINDING_FECES.toString()).size(), is(2));


    }
}