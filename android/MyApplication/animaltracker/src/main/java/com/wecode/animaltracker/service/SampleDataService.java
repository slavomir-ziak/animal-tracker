package com.wecode.animaltracker.service;

import com.wecode.animaltracker.model.Sample;

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
}
