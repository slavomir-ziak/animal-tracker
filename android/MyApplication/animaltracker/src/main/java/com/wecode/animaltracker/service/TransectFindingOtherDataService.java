package com.wecode.animaltracker.service;

import com.wecode.animaltracker.model.findings.TransectFindingFootprints;
import com.wecode.animaltracker.model.findings.TransectFindingOther;

import java.util.List;

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

    public List<TransectFindingOther> findByTransectFindingId(Long transectFindingId) {
        return TransectFindingOther.find(TransectFindingOther.class, "transect_finding_id=?", transectFindingId.toString());
    }

}
