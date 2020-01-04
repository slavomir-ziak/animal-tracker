package com.wecode.animaltracker.service;

import android.support.test.runner.AndroidJUnit4;

import com.wecode.animaltracker.model.findings.TransectFindingOther;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class TransectFindingOtherDataServiceTest {

    private TransectFindingOtherDataService service = TransectFindingOtherDataService.getInstance();

    @Before
    public void setUp() {
        service.deleteAll();
    }

    @Test
    public void testFindByTransectFindingId() {
        prepareData();

        List<TransectFindingOther> byTransectFindingId = service.findByTransectFindingId(1L);
        assertThat(byTransectFindingId.size(), is(1));
    }

    @Test
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