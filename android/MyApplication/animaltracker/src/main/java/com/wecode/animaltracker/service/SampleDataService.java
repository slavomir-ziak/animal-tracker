package com.wecode.animaltracker.service;

import android.annotation.SuppressLint;
import android.util.Log;

import com.wecode.animaltracker.Globals;
import com.wecode.animaltracker.model.Sample;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

/**
 * Created by sziak on 10/28/2015.
 */
public class SampleDataService extends AbstractDataService<Sample> {

    private static final SampleDataService INSTANCE = new SampleDataService();

    private SampleDataService(){
        super(Sample.class);
    }

    public static SampleDataService getInstance() {
        return INSTANCE;
    }

    public List<Sample> findByTransectFindingId(Long transectFindingId) {
        return Sample.find(Sample.class, "transect_finding_id=?", transectFindingId.toString());
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

        List<Sample> samples = Sample.find(Sample.class, "created between ? and ? order by sample_sequence_number desc", Long.toString(dateFrom.getTime()), Long.toString(dateTo.getTime()));
        System.out.println(samples);
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
