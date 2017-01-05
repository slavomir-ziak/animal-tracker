package com.wecode.animaltracker.service;

import com.wecode.animaltracker.model.CodeList;
import com.wecode.animaltracker.model.Habitat;
import com.wecode.animaltracker.model.Photo;
import com.wecode.animaltracker.util.Assert;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

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

    /**
     *
     * @param name
     * @return LinkedList, because we add to the beginning of this list.
     */
    public List<CodeList> findByName(String name) {
        LinkedList<CodeList> codeLists = new LinkedList<>();
        codeLists.addAll(CodeList.find(CodeList.class, "name = ?", name));
        return codeLists;
    }

    public CodeList findByNameAndLocalisedValue(String name, String localisedValue) {
        String valueColumn = Locale.getDefault().getLanguage().equals("sk") ? "value_sk" : "value";
        List<CodeList> codeLists = CodeList.find(CodeList.class, "name = ? and " + valueColumn + " = ?", name, localisedValue);
        Assert.isTrue("more than 1 CodeList row with name=" + name + " and " + valueColumn + "=" + localisedValue, codeLists.size() <= 1);
        return codeLists.size() == 1 ? codeLists.get(0) : null;
    }


    public String getLocalisedValueByNameAndValue(String name, String value) {
        if (value == null ) {
            return "";
        }
        List<CodeList> codeLists = CodeList.find(CodeList.class, "name = ? and value= ?", name, value);
        Assert.isTrue("more than 1 CodeList row with name=" + name + " and " + value + "=" + value, codeLists.size() <= 1);
        return codeLists.size() == 1 ? codeLists.get(0).getLocalisedValue() : value;
    }
}
