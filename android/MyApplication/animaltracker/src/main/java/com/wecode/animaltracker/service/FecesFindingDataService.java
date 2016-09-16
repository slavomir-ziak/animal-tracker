package com.wecode.animaltracker.service;

import com.wecode.animaltracker.model.findings.TransectFindingFeces;

/**
 * Created by SZIAK on 9/15/2016.
 */
public class FecesFindingDataService extends AbstractDataService<TransectFindingFeces> {

    private static final FecesFindingDataService INSTANCE = new FecesFindingDataService();

    protected FecesFindingDataService() {
        super(TransectFindingFeces.class);
    }

    public static FecesFindingDataService getInstance() {
        return INSTANCE;
    }
}
