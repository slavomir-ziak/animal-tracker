package com.wecode.animaltracker.service;

import com.j256.ormlite.dao.Dao;
import com.wecode.animaltracker.model.findings.TransectFindingFeces;
import com.wecode.animaltracker.model.findings.TransectFindingOther;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by SZIAK on 9/15/2016.
 */
public class TransectFindingOtherDataService extends AbstractDataService<TransectFindingOther> {

    private static TransectFindingOtherDataService INSTANCE;

    private TransectFindingOtherDataService(Dao<TransectFindingOther, Long> dao) {
        super(dao);
    }

    public static TransectFindingOtherDataService getInstance() {
        if (INSTANCE == null) {
            throw new IllegalStateException("INSTANCE is null, initialize first");
        }
        return INSTANCE;
    }

    public static void initialize(Dao<TransectFindingOther, Long> dao) {
        TransectFindingOtherDataService.INSTANCE = new TransectFindingOtherDataService(dao);
    }

    public List<TransectFindingOther> findByTransectFindingId(Long transectFindingId) {
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
    }}
