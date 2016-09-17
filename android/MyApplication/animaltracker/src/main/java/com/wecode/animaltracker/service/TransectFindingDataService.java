package com.wecode.animaltracker.service;

import com.wecode.animaltracker.model.Persistable;
import com.wecode.animaltracker.model.findings.TransectFinding;
import com.wecode.animaltracker.model.findings.TransectFindingFeces;
import com.wecode.animaltracker.model.findings.TransectFindingFootprints;
import com.wecode.animaltracker.model.findings.TransectFindingOther;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

    public List<Persistable> findFindingDetails(Long transectFindingId) {

        List<Persistable> results = new ArrayList<>();
        results.addAll(TransectFindingFeces.find(TransectFindingFeces.class, "transect_finding_id=?", transectFindingId.toString()));
        results.addAll(TransectFindingFootprints.find(TransectFindingFootprints.class, "transect_finding_id=?", transectFindingId.toString()));
        results.addAll(TransectFindingOther.find(TransectFindingOther.class, "transect_finding_id=?", transectFindingId.toString()));
        Collections.sort(results);
        return results;
    }




}
