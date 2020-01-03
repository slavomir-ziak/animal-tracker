package com.wecode.animaltracker.service;

import com.wecode.animaltracker.model.findings.TransectFindingOther;

import junit.framework.TestCase;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class TransectFindingOtherDataServiceTest extends TestCase {

    private TransectFindingOtherDataService service = TransectFindingOtherDataService.getInstance();

    public void setUp() {
        service.deleteAll();
    }

    public void testFindByTransectFindingId() {
        prepareData();

        List<TransectFindingOther> byTransectFindingId = service.findByTransectFindingId(1L);
        assertThat(byTransectFindingId.size(), is(1));
    }

    public void testCountByTransectFindingId() {
        prepareData();
        assertThat(service.countByTransectFindingId(2L), is(2L));
    }

    private void prepareData() {
        TransectFindingOther transectFindingOther = new TransectFindingOther();
        transectFindingOther.setAge("test");
        transectFindingOther.setTransectFindingId(1L);
        service.save(transectFindingOther);
        assertThat(transectFindingOther.getId(), notNullValue());

        transectFindingOther = new TransectFindingOther();
        transectFindingOther.setAge("test");
        transectFindingOther.setTransectFindingId(2L);
        service.save(transectFindingOther);
        assertThat(transectFindingOther.getId(), notNullValue());

        transectFindingOther = new TransectFindingOther();
        transectFindingOther.setAge("test");
        transectFindingOther.setTransectFindingId(2L);
        service.save(transectFindingOther);
        assertThat(transectFindingOther.getId(), notNullValue());
    }

}