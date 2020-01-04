package com.wecode.animaltracker.service;

import android.support.test.runner.AndroidJUnit4;

import com.wecode.animaltracker.model.findings.TransectFindingFeces;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class TransectFindingFecesDataServiceTest {

    private TransectFindingFecesDataService service = TransectFindingFecesDataService.getInstance();

    @Before
    public void setUp() {
        service.deleteAll();
    }

    @Test
    public void testFindByTransectFindingId() {
        prepareData();

        List<TransectFindingFeces> byTransectFindingId = service.findByTransectFindingId(1L);
        assertThat(byTransectFindingId.size(), is(1));
    }

    @Test
    public void testCountByTransectFindingId() {
        prepareData();
        assertThat(service.countByTransectFindingId(2L), is(2L));
    }

    private void prepareData() {
        TransectFindingFeces transectFindingFeces = new TransectFindingFeces();
        transectFindingFeces.setAge("test");
        transectFindingFeces.setTransectFindingId(1L);
        service.save(transectFindingFeces);
        assertThat(transectFindingFeces.getId(), notNullValue());

        transectFindingFeces = new TransectFindingFeces();
        transectFindingFeces.setAge("test");
        transectFindingFeces.setTransectFindingId(2L);
        service.save(transectFindingFeces);
        assertThat(transectFindingFeces.getId(), notNullValue());

        transectFindingFeces = new TransectFindingFeces();
        transectFindingFeces.setAge("test");
        transectFindingFeces.setTransectFindingId(2L);
        service.save(transectFindingFeces);
        assertThat(transectFindingFeces.getId(), notNullValue());
    }

}