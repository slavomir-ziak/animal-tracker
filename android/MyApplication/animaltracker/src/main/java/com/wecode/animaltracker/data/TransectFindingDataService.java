package com.wecode.animaltracker.data;

import com.wecode.animaltracker.model.TransectFinding;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sziak on 10/28/2015.
 */
public class TransectFindingDataService extends AbstractDataService<TransectFinding> {

    private static final TransectFindingDataService INSTANCE = new TransectFindingDataService();

    private Map<Long, TransectFinding> data = new HashMap<>();

    private TransectFindingDataService(){}

    public static TransectFindingDataService getInstance() {
        return INSTANCE;
    }

    @Override
    protected Map<Long, TransectFinding> getData() {
        return data;
    }


    {
        TransectFinding transectFinding = new TransectFinding();

        transectFinding.setId(1L);
        transectFinding.setType("Footprints");
        transectFinding.setSpecies("Wolf");
        getData().put(transectFinding.getId(), transectFinding);

        transectFinding = new TransectFinding();
        transectFinding.setId(2L);
        transectFinding.setType("Feces");
        transectFinding.setSpecies("Lynx");
        getData().put(transectFinding.getId(), transectFinding);

        transectFinding = new TransectFinding();
        transectFinding.setId(3L);
        transectFinding.setType("Urine");
        transectFinding.setSpecies("Red fox");
        getData().put(transectFinding.getId(), transectFinding);
    }

}
