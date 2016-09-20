package com.wecode.animaltracker.service;

import com.wecode.animaltracker.model.findings.TransectFindingFeces;
import com.wecode.animaltracker.model.findings.TransectFindingFootprints;
import com.wecode.animaltracker.model.findings.TransectFindingOther;

import java.util.List;

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

    public List<TransectFindingFootprints> findByTransectFindingId(Long transectFindingId) {
        return TransectFindingFootprints.find(TransectFindingFootprints.class, "transect_finding_id=?", transectFindingId.toString());
    }
}
