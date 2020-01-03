package com.wecode.animaltracker.service;

import com.wecode.animaltracker.model.findings.TransectFindingFeces;

import junit.framework.TestCase;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class TransectFindingFecesDataServiceTest extends TestCase {

    private TransectFindingFecesDataService service = TransectFindingFecesDataService.getInstance();

    public void setUp() {
        service.deleteAll();
    }

    public void testFindByTransectFindingId() {
        prepareData();

        List<TransectFindingFeces> byTransectFindingId = service.findByTransectFindingId(1L);
        assertThat(byTransectFindingId.size(), is(1));
    }

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