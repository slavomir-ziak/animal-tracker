package com.wecode.animaltracker.model;

/**
 * Created by SZIAK on 8/31/2016.
 */
public class CodeList extends Persistable{

    public static final String SOURCE_USER = "USER";

    public static final String SOURCE_APP = "APP";

    private String name;

    private String value;

    private String icon;

    /**
     * values: USER - user added himself, APP - added by application insert/update
     */
    private String source;

    public CodeList() {
    }

    public CodeList(String name, String value, String icon, String source) {
        this.name = name;
        this.value = value;
        this.icon = icon;
        this.source = source;
    }

    public CodeList(Long id, String value) {
        setId(id);
        this.value = value;
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

    @Override
    public String toString() {
        return "CodeList{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", icon='" + icon + '\'' +
                ", source='" + source + '\'' +
                "} " + super.toString();
    }

}
