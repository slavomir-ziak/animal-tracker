package com.wecode.animaltracker.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Locale;

/**
 * Created by SZIAK on 8/31/2016.
 */
@DatabaseTable(tableName = "CODE_LIST")
public class CodeList extends Persistable {

    public static final String SOURCE_USER = "USER";

    public static final String SOURCE_APP = "APP";

    public static final String NAME_COLUMN = "NAME";

    public static final String VALUE_COLUMN = "VALUE";

    public static final String VALUE_SK_COLUMN = "VALUE_SK";

    public static final String ICON_COLUMN = "ICON";

    public static final String SOURCE_COLUMN = "SOURCE";

    @DatabaseField(columnName = NAME_COLUMN)
    private String name;

    @DatabaseField(columnName = VALUE_COLUMN)
    private String value;

    @DatabaseField(columnName = VALUE_SK_COLUMN)
    private String valueSk;

    @DatabaseField(columnName = ICON_COLUMN)
    private String icon;

    /**
     * values: USER - user added himself, APP - added by application insert/update
     */
    @DatabaseField(columnName = SOURCE_COLUMN)
    private String source;

    public CodeList() {
    }

    public CodeList(String name, String value, String valueSk, String icon, String source) {
        this.name = name;
        this.value = value;
        this.valueSk = valueSk;
        this.icon = icon;
        this.source = source;
    }

    public CodeList(Long id, String value, String valueSk) {
        setId(id);
        this.value = value;
        this.valueSk = valueSk;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getValueSk() {
        return valueSk;
    }

    public void setValueSk(String valueSk) {
        this.valueSk = valueSk;
    }

    public String getLocalisedValue() {
        if (Locale.getDefault().getLanguage().equals("sk")) {
            return getValueSk();
        } else {
            return getValue();
        }
    }

    @Override
    public String toString() {
        return "CodeList{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", icon='" + icon + '\'' +
                ", valueSk='" + valueSk + '\'' +
                ", source='" + source + '\'' +
                "} " + super.toString();
    }

}
