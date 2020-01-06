package com.wecode.animaltracker.service;

import com.j256.ormlite.dao.Dao;
import com.wecode.animaltracker.model.Persistable;
import com.wecode.animaltracker.model.TransectFindingSite;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by sziak on 10/28/2015.
 */
public class TransectFindingSiteDataService extends AbstractDataService<TransectFindingSite> {

    private static TransectFindingSiteDataService INSTANCE;

    private TransectFindingSiteDataService(Dao<TransectFindingSite, Long> dao) {
        super(dao);
    }

    public static TransectFindingSiteDataService getInstance() {
        if (INSTANCE == null) {
            throw new IllegalStateException("INSTANCE is null, initialize first");
        }
        return INSTANCE;
    }

    public static void initialize(Dao<TransectFindingSite, Long> dao) {
        TransectFindingSiteDataService.INSTANCE = new TransectFindingSiteDataService(dao);
    }

    public List<TransectFindingSite> findByTransectId(Long transectId) {

        try {
            return dao.queryBuilder().orderBy(TransectFindingSite.ID_COLUMN, true)
                    .where().eq(TransectFindingSite.TRANSECT_ID, transectId).query();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Persistable> findTransectFindings(Long transectFindingId) {
        List<Persistable> results = new ArrayList<>();
        results.addAll(TransectFindingFecesDataService.getInstance().findByTransectFindingId(transectFindingId));
        results.addAll(TransectFindingFootprintsDataService.getInstance().findByTransectFindingId(transectFindingId));
        results.addAll(TransectFindingOtherDataService.getInstance().findByTransectFindingId(transectFindingId));
        Collections.sort(results);
        return results;
    }




}
