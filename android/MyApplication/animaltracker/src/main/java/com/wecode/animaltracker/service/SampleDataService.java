package com.wecode.animaltracker.service;

import com.wecode.animaltracker.model.Habitat;
import com.wecode.animaltracker.model.Sample;
import com.wecode.animaltracker.model.TransectFinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sziak on 10/28/2015.
 */
public class SampleDataService extends AbstractDataService<Sample> {

    private Map<Long, Sample> data = new HashMap<>();
    {
        data.put(getNextId(), new Sample(1L, "sample 1", 1L));
        data.put(getNextId(), new Sample(2L, "sample 2", 1L));
    }
    private static final SampleDataService INSTANCE = new SampleDataService();

    private SampleDataService(){}

    public static SampleDataService getInstance() {
        return INSTANCE;
    }

    @Override
    protected Map<Long, Sample> getData() {
        return data;
    }

    public List<Sample> findByTransectFindingId(Long transectFindingId) {
        List<Sample> results = new ArrayList<>();

        for (Sample transectFinding : data.values()) {
            if (transectFinding.getTransectFindingDetailId().equals(transectFindingId)) {
                results.add(transectFinding);
            }
        }
        return results;
    }
}
