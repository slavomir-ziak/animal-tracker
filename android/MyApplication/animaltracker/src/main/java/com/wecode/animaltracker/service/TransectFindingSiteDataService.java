package com.wecode.animaltracker.service;

import com.wecode.animaltracker.model.Persistable;
import com.wecode.animaltracker.model.TransectFindingSite;
import com.wecode.animaltracker.model.findings.TransectFindingFeces;
import com.wecode.animaltracker.model.findings.TransectFindingFootprints;
import com.wecode.animaltracker.model.findings.TransectFindingOther;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by sziak on 10/28/2015.
 */
public class TransectFindingSiteDataService extends AbstractDataService<TransectFindingSite> {

    private static final TransectFindingSiteDataService INSTANCE = new TransectFindingSiteDataService();

    private TransectFindingSiteDataService(){
        super(TransectFindingSite.class);
    }

    public static TransectFindingSiteDataService getInstance() {
        return INSTANCE;
    }

    public List<TransectFindingSite> findByTransectId(Long transectId) {
        return TransectFindingSite.find(TransectFindingSite.class, "transect_id=? order by id asc", transectId.toString());
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
