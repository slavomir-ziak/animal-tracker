package com.wecode.animaltracker.service;

import android.annotation.SuppressLint;

import com.j256.ormlite.dao.Dao;
import com.wecode.animaltracker.model.EntityName;
import com.wecode.animaltracker.model.Sample;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by sziak on 10/28/2015.
 */
public class SampleDataService extends AbstractDataService<Sample> {

    private static SampleDataService INSTANCE;

    private SampleDataService(Dao<Sample, Long> dao) {
        super(dao);
    }

    public static SampleDataService getInstance() {
        if (INSTANCE == null) {
            throw new IllegalStateException("INSTANCE is null, initialize first");
        }
        return INSTANCE;
    }

    public static void initialize(Dao<Sample, Long> dao) {
        SampleDataService.INSTANCE = new SampleDataService(dao);
    }

    public List<Sample> findByTransectFindingId(Long transectFindingId, EntityName sampleType) {
        try {
            return dao.queryBuilder().where().eq(Sample.TRANSECT_FINDING_ID_COLUMN, transectFindingId)
                    .and()
                    .eq(Sample.SAMPLE_TYPE_COLUMN, sampleType).query();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Integer nextMaxSequenceNumber() {

        Calendar midnightCalendar = Calendar.getInstance();
        midnightCalendar.set(GregorianCalendar.HOUR_OF_DAY, 0);
        midnightCalendar.set(GregorianCalendar.MINUTE, 0);
        midnightCalendar.set(GregorianCalendar.SECOND, 0);
        midnightCalendar.set(GregorianCalendar.MILLISECOND, 0);

        Date dateFrom = midnightCalendar.getTime();

        midnightCalendar.add(GregorianCalendar.DAY_OF_MONTH, 1);
        Date dateTo = midnightCalendar.getTime();

        List<Sample> samples;

        try {

            samples = dao.queryBuilder()
                    .orderBy(Sample.SAMPLE_SEQUENCE_NUMBER_COLUMN, false)
                    .where().between(Sample.CREATED_COLUMN, dateFrom, dateTo)
                    .query();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (samples.size() > 0) {
            Integer sampleSequenceNumber = samples.get(0).getSampleSequenceNumber();
            if (sampleSequenceNumber != null){
                return sampleSequenceNumber + 1;
            }
        }

        return 1;
    }

    public String[] generateSampleNumber(String trackerName) {
        Integer integer = nextMaxSequenceNumber();
        return new String[]{
                String.format("%s_%s_%s", createInitials(trackerName), getDate(), integer),
                integer.toString()
        };
    }

    @SuppressLint("SimpleDateFormat")
    private String getDate() {
        return new SimpleDateFormat("yyyyMMdd").format(new Date());
    }

    private String createInitials(String trackerName) {
        String[] split = trackerName.split(" ");
        StringBuilder sb = new StringBuilder();
        for (String s : split) {
            sb.append(s.charAt(0));
        }
        return sb.toString().toUpperCase();
    }

}
