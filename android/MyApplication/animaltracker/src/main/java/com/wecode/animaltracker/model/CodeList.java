package com.wecode.animaltracker.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.wecode.animaltracker.i18n.LocalisationHelper;

/**
 * Created by SZIAK on 8/31/2016.
 */
@DatabaseTable(tableName = "CODE_LIST")
public class CodeList extends Persistable {

    public enum Name {
        habitatTypes,
        habitatTrackTypes,
        habitatForestAgeTypes,
        habitatTreeTypes,
        habitatForestTypes,
        findingSpeciesTypes,
        findingAge,
        findingObservation,
        fecesState,
        fecesPrey,
        findingOtherEvidence,
        transectRegion,
        findingSubstract
    }

    public static final String SOURCE_USER = "USER";

    public static final String SOURCE_APP = "APP";

    public static final String NAME_COLUMN = "NAME";

    public static final String VALUE_COLUMN = "VALUE";

    public static final String VALUE_SK_COLUMN = "VALUE_SK";

    public static final String VALUE_FR_COLUMN = "VALUE_FR";

    public static final String VALUE_PT_COLUMN = "VALUE_PT";

    public static final String ICON_COLUMN = "ICON";

    public static final String SOURCE_COLUMN = "SOURCE";

    @DatabaseField(columnName = NAME_COLUMN)
    private String name;

    @DatabaseField(columnName = VALUE_COLUMN)
    private String value;

    @DatabaseField(columnName = VALUE_SK_COLUMN)
    private String valueSk;

    @DatabaseField(columnName = VALUE_PT_COLUMN)
    private String valuePt;

    @DatabaseField(columnName = VALUE_FR_COLUMN)
    private String valueFr;

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

    public String getValuePt() {
        return valuePt;
    }

    public void setValuePt(String valuePt) {
        this.valuePt = valuePt;
    }

    public String getValueFr() {
        return valueFr;
    }

    public void setValueFr(String valueFr) {
        this.valueFr = valueFr;
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
        return LocalisationHelper.getLocalisedValue(this);
    }

    public void setAllValuesSame(String value) {
        LocalisationHelper.setAllValuesSame(value, this);
    }

    @Override
    public String toString() {
        return "CodeList{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", valueSk='" + valueSk + '\'' +
                ", valuePt='" + valuePt + '\'' +
                ", valueFr='" + valueFr + '\'' +
                ", icon='" + icon + '\'' +
                ", source='" + source + '\'' +
                "} " + super.toString();
    }
}
