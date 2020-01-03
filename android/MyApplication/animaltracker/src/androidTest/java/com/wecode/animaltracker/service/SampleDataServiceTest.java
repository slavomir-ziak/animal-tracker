package com.wecode.animaltracker.service;

import com.wecode.animaltracker.model.EntityName;
import com.wecode.animaltracker.model.Sample;

import junit.framework.TestCase;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class SampleDataServiceTest extends TestCase {

    SampleDataService service = SampleDataService.getInstance();

    public void setUp() {
        service.deleteAll();
    }

    public void testFindByTransectFindingId() {

        Sample sample = service.save(new Sample("1", 2L, 3, EntityName.TRANECT_FINDING_FECES));
        assertThat(sample.getId(), notNullValue());

        sample = service.save(new Sample("3", 2L, 3, EntityName.TRANECT_FINDING_SITE));
        assertThat(sample.getId(), notNullValue());

        sample = service.save(new Sample("4", 2L, 3, EntityName.TRANECT_FINDING_SITE));
        assertThat(sample.getId(), notNullValue());

        List<Sample> byTransectFindingId = service.findByTransectFindingId(2L, EntityName.TRANECT_FINDING_SITE);

        assertThat(byTransectFindingId.size(), is(2));
        assertThat(byTransectFindingId.get(0).getSampleNumber(), is("3"));
        assertThat(byTransectFindingId.get(1).getSampleNumber(), is("4"));

    }

    public void testGenerateSampleNumber_simple() {
        String[] strings = service.generateSampleNumber("test test");
        String sampleNuber = strings[1];
        assertThat(sampleNuber, is("1"));
    }

    public void testGenerateSampleNumber_complex() {

        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DAY_OF_MONTH, -1);

        createSample(yesterday.getTime(), 1);
        createSample(yesterday.getTime(), 2);
        createSample(yesterday.getTime(), 3);

        Calendar tommorow = Calendar.getInstance();
        tommorow.add(Calendar.DAY_OF_MONTH, 1);

        createSample(tommorow.getTime(), 1);
        createSample(tommorow.getTime(), 2);
        createSample(tommorow.getTime(), 3);

        createSample(new Date(), 1);
        createSample(new Date(), 2);
        createSample(new Date(), 3);
        createSample(new Date(), 4);

        assertThat(service.listAll().size(), is(10));

        String[] strings = service.generateSampleNumber("test test");
        String sampleNuber = strings[1];
        assertThat(sampleNuber, is("5"));
    }

    private void createSample(Date created, Integer sequence) {
        Sample sample = service.save(new Sample("1", 1L, sequence, EntityName.TRANECT_FINDING_FECES));
        sample.setCreated(created);
        sample = service.save(sample);
        assertThat(sample.getId(), notNullValue());
        assertThat(sample.getCreated(), is(created));
    }
}