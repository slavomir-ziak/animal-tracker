package com.wecode.animaltracker.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by sziak on 10-Apr-16.
 */
@DatabaseTable(tableName = "TRANSECT_FINDING_SITE")
public class TransectFindingSite extends Persistable {

    public static final String TRANSECT_ID = "TRANSECT_ID";
    public static final String HABITAT_ID = "HABITAT_ID";
    public static final String SPECIES = "SPECIES";
    public static final String LOCATION_LATITUDE = "LOCATION_LATITUDE";
    public static final String LOCATION_LONGITUDE = "LOCATION_LONGITUDE";
    public static final String LOCATION_ELEVATION = "LOCATION_ELEVATION";
    public static final String BEFORE_AFTER_RECENT_SNOW = "BEFORE_AFTER_RECENT_SNOW";

    //FK
    @DatabaseField(columnName = TRANSECT_ID)
    private Long transectId;

    //FK
    @DatabaseField(columnName = HABITAT_ID)
    private Long habitatId;

    @DatabaseField(columnName = SPECIES)
    private String species;

    @DatabaseField(columnName = LOCATION_LATITUDE)
    private Double locationLatitude;

    @DatabaseField(columnName = LOCATION_LONGITUDE)
    private Double locationLongitude;

    @DatabaseField(columnName = LOCATION_ELEVATION)
    private Double locationElevation;

    @DatabaseField(columnName = BEFORE_AFTER_RECENT_SNOW)
    private String beforeAfterRecentSnow;

    public TransectFindingSite() {
    }

    public Double getLocationLatitude() {
        return locationLatitude;
    }

    public void setLocationLatitude(Double locationLatitude) {
        this.locationLatitude = locationLatitude;
    }

    public Double getLocationLongitude() {
        return locationLongitude;
    }

    public void setLocationLongitude(Double locationLongitude) {
        this.locationLongitude = locationLongitude;
    }

    public boolean hasLocation() {
        return getLocationLatitude() != null && getLocationLatitude() != null;
    }

    public Long getTransectId() {
        return transectId;
    }

    public void setTransectId(Long transectId) {
        this.transectId = transectId;
    }

    public Long getHabitatId() {
        return habitatId;
    }

    public void setHabitatId(Long habitatId) {
        this.habitatId = habitatId;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getBeforeAfterRecentSnow() {
        return beforeAfterRecentSnow;
    }

    public void setBeforeAfterRecentSnow(String beforeAfterRecentSnow) {
        this.beforeAfterRecentSnow = beforeAfterRecentSnow;
    }

    public Double getLocationElevation() {
        return locationElevation;
    }

    public void setLocationElevation(Double locationElevation) {
        this.locationElevation = locationElevation;
    }

    @Override
    public String toString() {
        return "TransectFindingSite{" +
                "transectId=" + transectId +
                ", habitatId=" + habitatId +
                ", species='" + species + '\'' +
                ", locationLatitude=" + locationLatitude +
                ", locationLongitude=" + locationLongitude +
                ", locationElevation=" + locationElevation +
                ", beforeAfterRecentSnow='" + beforeAfterRecentSnow + '\'' +
                "} " + super.toString();
    }
}
