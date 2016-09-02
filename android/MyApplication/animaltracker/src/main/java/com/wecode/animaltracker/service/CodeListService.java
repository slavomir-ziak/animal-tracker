package com.wecode.animaltracker.service;

import com.wecode.animaltracker.model.CodeList;
import com.wecode.animaltracker.model.Habitat;
import com.wecode.animaltracker.model.Photo;
import com.wecode.animaltracker.util.Assert;

import java.util.List;

/**
 * Created by SZIAK on 9/1/2016.
 */
public class CodeListService extends AbstractDataService<CodeList> {

    private static final CodeListService INSTANCE = new CodeListService(CodeList.class);

    private CodeListService(Class<CodeList> persistentClass) {
        super(persistentClass);
    }

    public static CodeListService getInstance() {
        return INSTANCE;
    }

    public List<CodeList> findByName(String name) {
        return CodeList.find(CodeList.class, "name = ? ORDER BY value", name);
    }

    public CodeList findByNameAndValue(String name, String value) {
        List<CodeList> codeLists = CodeList.find(CodeList.class, "name = ? and value = ?", name, value);
        Assert.isTrue("more than 1 CodeList row with name="+name+" and value="+value, codeLists.size() <= 1);
        return codeLists.size() == 1 ? codeLists.get(0) : null;
    }


}
