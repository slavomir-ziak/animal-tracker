package com.wecode.animaltracker.service;

import com.wecode.animaltracker.model.CodeList;

import junit.framework.TestCase;

import java.util.List;
import java.util.Locale;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;


public class CodeListServiceTest extends TestCase {

    private CodeListService service = CodeListService.getInstance();

    public void setUp() {
        service.deleteAll();
    }

    public void testFindByName() {

        CodeList codeList = service.save(new CodeList("test", "value", null, null, null));
        assertThat(codeList.getId(), notNullValue());

        codeList = service.save(new CodeList("test3", "value3", null, null, null));
        assertThat(codeList.getId(), notNullValue());

        List<CodeList> test = service.findByName("test");
        assertThat(test.size(), is(1));
        assertThat(test.get(0).getName(), is("test"));
    }

    public void testFindByNameAndLocalisedValue() {
        assertThat(Locale.getDefault().getLanguage(), is("sk"));

        CodeList codeList = service.save(new CodeList("test", "value", "hodnota", null, null));
        assertThat(codeList.getId(), notNullValue());

        codeList = service.save( new CodeList("test3", "value3", "hodnota2", null, null));
        assertThat(codeList.getId(), notNullValue());

        CodeList test = service.findByNameAndLocalisedValue("test", "hodnota");
        assertThat(test.getValueSk(), is("hodnota"));
        assertThat(test.getName(), is("test"));

    }

    public void testGetLocalisedValueByNameAndValue() {

        CodeList codeList = service.save(new CodeList("test", "value", "hodnota", null, null));
        assertThat(codeList.getId(), notNullValue());

        codeList = service.save( new CodeList("test3", "value3", "hodnota2", null, null));
        assertThat(codeList.getId(), notNullValue());

        String test = service.getLocalisedValueByNameAndValue("test", "hodnota");
        assertThat(test, is("hodnota"));

    }

    public void testDeleteAll() {

        CodeList codeList = service.save(new CodeList("test", "value", "hodnota", null, null));
        assertThat(codeList.getId(), notNullValue());

        assertThat(service.findByName("test").size(), is(1));

        service.deleteAll();

        assertThat(service.findByName("test").size(), is(0));
    }
}