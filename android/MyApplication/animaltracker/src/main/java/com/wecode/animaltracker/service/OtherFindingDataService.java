package com.wecode.animaltracker.service;

import com.wecode.animaltracker.model.findings.TransectFindingOther;

/**
 * Created by SZIAK on 9/15/2016.
 */
public class OtherFindingDataService extends AbstractDataService<TransectFindingOther> {

    private static final OtherFindingDataService INSTANCE = new OtherFindingDataService();

    private OtherFindingDataService(){
        super(TransectFindingOther.class);
    }

    public static OtherFindingDataService getInstance() {
        return INSTANCE;
    }

}
