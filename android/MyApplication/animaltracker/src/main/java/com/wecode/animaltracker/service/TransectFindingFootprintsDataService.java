package com.wecode.animaltracker.service;

import com.j256.ormlite.dao.Dao;
import com.wecode.animaltracker.model.findings.TransectFindingFeces;
import com.wecode.animaltracker.model.findings.TransectFindingFootprints;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by SZIAK on 9/15/2016.
 */
public class TransectFindingFootprintsDataService extends AbstractDataService<TransectFindingFootprints> {

    private static TransectFindingFootprintsDataService INSTANCE;

    private TransectFindingFootprintsDataService(Dao<TransectFindingFootprints, Long> dao) {
        super(dao);
    }

    public static TransectFindingFootprintsDataService getInstance() {
        if (INSTANCE == null) {
            throw new IllegalStateException("INSTANCE is null, initialize first");
        }
        return INSTANCE;
    }

    public static void initialize(Dao<TransectFindingFootprints, Long> dao) {
        TransectFindingFootprintsDataService.INSTANCE = new TransectFindingFootprintsDataService(dao);
    }

    public List<TransectFindingFootprints> findByTransectFindingId(Long transectFindingId) {
        try {
            return dao.queryForEq(TransectFindingFeces.TRANSECT_FINDING_ID_COLUMN, transectFindingId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public long countByTransectFindingId(Long transectFindingId) {
        try {
            return dao.queryBuilder()
                    .where().eq(TransectFindingFeces.TRANSECT_FINDING_ID_COLUMN, transectFindingId)
                    .countOf();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
