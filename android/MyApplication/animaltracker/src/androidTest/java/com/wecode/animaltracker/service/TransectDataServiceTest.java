package com.wecode.animaltracker.service;

import android.support.test.runner.AndroidJUnit4;

import com.wecode.animaltracker.model.Transect;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class TransectDataServiceTest {

    TransectDataService service = TransectDataService.getInstance();

    @Before
    public void setUp() {
        service.deleteAll();
    }

    @Test
    public void save() {
        Transect transect = saveTransect();
        assertThat(transect.getId(), notNullValue());
    }

    private Transect saveTransect() {
        Transect transect = new Transect();
        transect.setSquare(0);
        transect.setLocalisation("asdf");
        transect.setEndDateTime(new Date());
        transect.setEndElevation(11.11111);
        service.save(transect);
        return transect;
    }

    @Test
    public void find() {
        Transect transect = saveTransect();
        Transect sameTransect = service.find(transect.getId());
        assertThat(transect, equalTo(sameTransect));
    }

    @Test
    public void listAll() {
        saveTransect();
        saveTransect();
        saveTransect();

        assertThat(service.listAll().size(), is(3));

    }

    @Test
    public void deleteAll() {

        saveTransect();
        saveTransect();
        saveTransect();

        assertThat(service.listAll().size(), is(3));

        service.deleteAll();

        assertThat(service.listAll().size(), is(0));
    }
}