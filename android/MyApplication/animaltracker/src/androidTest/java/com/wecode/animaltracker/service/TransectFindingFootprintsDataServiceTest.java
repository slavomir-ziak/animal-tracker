package com.wecode.animaltracker.service;

import android.support.test.runner.AndroidJUnit4;

import com.wecode.animaltracker.model.findings.TransectFindingFootprints;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class TransectFindingFootprintsDataServiceTest extends TestCase {

    private TransectFindingFootprintsDataService service = TransectFindingFootprintsDataService.getInstance();

    @Before
    public void setUp() {
        service.deleteAll();
    }

    @Test
    public void testFindByTransectFindingId() {
        prepareData();

        List<TransectFindingFootprints> byTransectFindingId = service.findByTransectFindingId(1L);
        assertThat(byTransectFindingId.size(), is(1));
    }

    @Test
    public void testCountByTransectFindingId() {
        prepareData();
        assertThat(service.countByTransectFindingId(2L), is(2L));
    }

    private void prepareData() {
        TransectFindingFootprints transectFindingFootprints = new TransectFindingFootprints();
        transectFindingFootprints.setAge("test");
        transectFindingFootprints.setTransectFindingId(1L);
        service.save(transectFindingFootprints);
        assertThat(transectFindingFootprints.getId(), notNullValue());

        transectFindingFootprints = new TransectFindingFootprints();
        transectFindingFootprints.setAge("test");
        transectFindingFootprints.setTransectFindingId(2L);
        service.save(transectFindingFootprints);
        assertThat(transectFindingFootprints.getId(), notNullValue());

        transectFindingFootprints = new TransectFindingFootprints();
        transectFindingFootprints.setAge("test");
        transectFindingFootprints.setTransectFindingId(2L);
        service.save(transectFindingFootprints);
        assertThat(transectFindingFootprints.getId(), notNullValue());
    }

}