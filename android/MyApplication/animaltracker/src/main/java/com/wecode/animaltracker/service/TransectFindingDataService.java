package com.wecode.animaltracker.service;

import com.wecode.animaltracker.model.TransectFinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sziak on 10/28/2015.
 */
public class TransectFindingDataService extends AbstractDataService<TransectFinding> {

    private Map<Long, TransectFinding> data = new HashMap<>();
    {
        TransectFinding transectFinding = new TransectFinding();

        transectFinding.setId(getNextId());
        transectFinding.setType("Footprints");
        transectFinding.setSpecies("Wolf");
        transectFinding.setTransectId(1L);
        getData().put(transectFinding.getId(), transectFinding);

        transectFinding = new TransectFinding();
        transectFinding.setId(getNextId());
        transectFinding.setType("Feces");
        transectFinding.setSpecies("Lynx");
        transectFinding.setTransectId(1L);
        getData().put(transectFinding.getId(), transectFinding);

        transectFinding = new TransectFinding();
        transectFinding.setId(getNextId());
        transectFinding.setType("Urine");
        transectFinding.setSpecies("Red fox");
        transectFinding.setTransectId(2L);
        getData().put(transectFinding.getId(), transectFinding);

    }

    private static final TransectFindingDataService INSTANCE = new TransectFindingDataService();

    private TransectFindingDataService(){}

    public static TransectFindingDataService getInstance() {
        return INSTANCE;
    }

    @Override
    protected Map<Long, TransectFinding> getData() {
        return data;
    }

    public List<TransectFinding> findByTransectId(Long transectId) {

        List<TransectFinding> results = new ArrayList<>();

        for (TransectFinding transectFinding : data.values()) {
            if (transectFinding.getTransectId().equals(transectId)) {
                results.add(transectFinding);
            }
        }
        return results;
    }
}
