package com.wecode.animaltracker.service;

import com.j256.ormlite.dao.Dao;
import com.wecode.animaltracker.model.CodeList;
import com.wecode.animaltracker.util.Assert;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * Created by SZIAK on 9/1/2016.
 */
public class CodeListService extends AbstractDataService<CodeList> {

    private static CodeListService INSTANCE;

    private CodeListService(Dao<CodeList, Long> dao) {
        super(dao);
    }

    public static CodeListService getInstance() {
        if (INSTANCE == null) {
            throw new IllegalStateException("INSTANCE is null, initialize first");
        }
        return INSTANCE;
    }

    public static void initialize(Dao<CodeList, Long> dao) {
        CodeListService.INSTANCE = new CodeListService(dao);
    }

    /**
     *
     * @param name
     * @return LinkedList, because we add to the beginning of this listAll.
     */
    public List<CodeList> findByName(String name) {
        LinkedList<CodeList> codeLists;
        try {
            codeLists = new LinkedList<>(dao.queryForEq(CodeList.NAME_COLUMN, name));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return codeLists;
    }

    public CodeList findByNameAndLocalisedValue(String name, String localisedValue) {
        String valueColumn = Locale.getDefault().getLanguage().equals("sk") ? CodeList.VALUE_SK_COLUMN : CodeList.VALUE_COLUMN;
        try {
            List<CodeList> codeLists = dao.queryBuilder()
                    .where().eq(CodeList.NAME_COLUMN, name)
                    .and().eq(valueColumn, localisedValue)
                    .query();

            Assert.isTrue("more than 1 CodeList row with name=" + name + " and " + valueColumn + "=" + localisedValue, codeLists.size() <= 1);
            return codeLists.size() == 1 ? codeLists.get(0) : null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public String getLocalisedValueByNameAndValue(String name, String value) {
        if (value == null ) {
            return "";
        }
        List<CodeList> codeLists = null;
        try {
            codeLists = dao.queryBuilder()
                    .where().eq(CodeList.NAME_COLUMN, name)
                    .and().eq(CodeList.VALUE_COLUMN, value)
                    .query();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Assert.isTrue("more than 1 CodeList row with name=" + name + " and " + value + "=" + value, codeLists.size() <= 1);
        return codeLists.size() == 1 ? codeLists.get(0).getLocalisedValue() : value;
    }

}
