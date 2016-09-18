package com.wecode.animaltracker.service;

import com.wecode.animaltracker.model.findings.TransectFindingOther;

/**
 * Created by SZIAK on 9/15/2016.
 */
public class TransectFindingOtherDataService extends AbstractDataService<TransectFindingOther> {

    private static final TransectFindingOtherDataService INSTANCE = new TransectFindingOtherDataService();

    private TransectFindingOtherDataService(){
        super(TransectFindingOther.class);
    }

    public static TransectFindingOtherDataService getInstance() {
        return INSTANCE;
    }

}
