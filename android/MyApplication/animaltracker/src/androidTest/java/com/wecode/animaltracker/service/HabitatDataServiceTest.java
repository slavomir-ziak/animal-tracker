package com.wecode.animaltracker.service;

import android.support.test.runner.AndroidJUnit4;

import com.wecode.animaltracker.model.Habitat;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class HabitatDataServiceTest extends TestCase {

    HabitatDataService service = HabitatDataService.getInstance();

    @Before
    public void setUp() throws Exception {
        service.deleteAll();
    }

    @Test
    public void testSave() {
        Habitat habitat = service.save(new Habitat("type", "track", "age", "test", "test"));
        assertThat(habitat.getId(), notNullValue());
    }

    @Test
    public void testFind() {
        Habitat habitat = service.save(new Habitat("type", "track", "age", "test", "test"));
        Habitat sameHabitat = service.find(habitat.getId());
        assertThat(habitat, equalTo(sameHabitat));
    }

    @Test
    public void testListAll() {

        service.save(new Habitat("type", "track", "age", "test", "test"));
        service.save(new Habitat("type", "track", "age", "test", "test"));
        service.save(new Habitat("type", "track", "age", "test", "test"));
        service.save(new Habitat("type", "track", "age", "test", "test"));

        assertThat(service.listAll().size(), is(4));
    }


}