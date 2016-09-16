package com.wecode.animaltracker.service;

import com.wecode.animaltracker.model.findings.TransectFindingFootprints;

/**
 * Created by SZIAK on 9/15/2016.
 */
public class FootprintsFindingDataService extends AbstractDataService<TransectFindingFootprints> {

    private static final FootprintsFindingDataService INSTANCE = new FootprintsFindingDataService();

    private FootprintsFindingDataService(){
        super(TransectFindingFootprints.class);
    }

    public static FootprintsFindingDataService getInstance() {
        return INSTANCE;
    }

}
