package com.wecode.animaltracker.model;

/**
 * Created by sziak on 11/1/2015.
 */
public class Habitat extends Persistable {

    private String type;
    private String track;
    private String forestAge;
    private String treeType;
    private String forestType;

    public Habitat() {
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
                "id='" + getId() + '\'' +
                "type='" + type + '\'' +
                ", track='" + track + '\'' +
                ", forestAge='" + forestAge + '\'' +
                ", treeType='" + treeType + '\'' +
                ", forestType='" + forestType + '\'' +
                '}';
    }
}
