package com.wecode.animaltracker.service;

import com.wecode.animaltracker.model.findings.TransectFindingFeces;
import com.wecode.animaltracker.model.findings.TransectFindingFootprints;

import java.util.List;

/**
 * Created by SZIAK on 9/15/2016.
 */
public class TransectFindingFecesDataService extends AbstractDataService<TransectFindingFeces> {

    private static final TransectFindingFecesDataService INSTANCE = new TransectFindingFecesDataService();

    protected TransectFindingFecesDataService() {
        super(TransectFindingFeces.class);
    }

    public static TransectFindingFecesDataService getInstance() {
        return INSTANCE;
    }

    public List<TransectFindingFeces> findByTransectFindingId(Long transectFindingId) {
        return TransectFindingFeces.find(TransectFindingFeces.class, "transect_finding_id=?", transectFindingId.toString());
    }
}
