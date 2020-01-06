package com.wecode.animaltracker.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by sziak on 11/1/2015.
 */
@DatabaseTable(tableName = "HABITAT")
public class Habitat extends Persistable {

    public static final String FOREST = "Forest";

    public static final String TYPE_COLUMN = "TYPE";

    public static final String TRACK_COLUMN = "TRACK";

    public static final String FOREST_AGE_COLUMN = "FOREST_AGE";

    public static final String TREE_TYPE_COLUMN = "TREE_TYPE";

    public static final String FOREST_TYPE_COLUMN = "FOREST_TYPE";

    @DatabaseField(columnName = TYPE_COLUMN)
    private String type;

    @DatabaseField(columnName = TRACK_COLUMN)
    private String track;

    @DatabaseField(columnName = FOREST_AGE_COLUMN)
    private String forestAge;

    @DatabaseField(columnName = TREE_TYPE_COLUMN)
    private String treeType;

    @DatabaseField(columnName = FOREST_TYPE_COLUMN)
    private String forestType;

    public Habitat() {
    }

    public Habitat(String type, String track, String forestAge, String treeType, String forestType) {
        this.type = type;
        this.track = track;
        this.forestAge = forestAge;
        this.treeType = treeType;
        this.forestType = forestType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public String getForestAge() {
        return forestAge;
    }

    public void setForestAge(String forestAge) {
        this.forestAge = forestAge;
    }

    public String getTreeType() {
        return treeType;
    }

    public void setTreeType(String treeType) {
        this.treeType = treeType;
    }

    public String getForestType() {
        return forestType;
    }

    public void setForestType(String forestType) {
        this.forestType = forestType;
    }

    @Override
    public String toString() {
        return "Habitat{" +
                "type='" + type + '\'' +
                ", track='" + track + '\'' +
                ", forestAge='" + forestAge + '\'' +
                ", treeType='" + treeType + '\'' +
                ", forestType='" + forestType + '\'' +
                "} " + super.toString();
    }

    public boolean isForest() {
        return Habitat.FOREST.equals(getType());
    }
}
