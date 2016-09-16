package com.wecode.animaltracker.service;

import com.wecode.animaltracker.model.findings.TransectFinding;

import java.util.List;

/**
 * Created by sziak on 10/28/2015.
 */
public class TransectFindingDataService extends AbstractDataService<TransectFinding> {

    private static final TransectFindingDataService INSTANCE = new TransectFindingDataService();

    private TransectFindingDataService(){
        super(TransectFinding.class);
    }

    public static TransectFindingDataService getInstance() {
        return INSTANCE;
    }

    public List<TransectFinding> findByTransectId(Long transectId) {
        return TransectFinding.find(TransectFinding.class, "transect_id=? order by id asc", transectId.toString());
    }
}
