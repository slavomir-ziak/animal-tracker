package com.wecode.animaltracker.service;

import com.wecode.animaltracker.model.findings.TransectFindingFootprints;

/**
 * Created by SZIAK on 9/15/2016.
 */
public class TransectFindingFootprintsDataService extends AbstractDataService<TransectFindingFootprints> {

    private static final TransectFindingFootprintsDataService INSTANCE = new TransectFindingFootprintsDataService();

    private TransectFindingFootprintsDataService(){
        super(TransectFindingFootprints.class);
    }

    public static TransectFindingFootprintsDataService getInstance() {
        return INSTANCE;
    }

}
