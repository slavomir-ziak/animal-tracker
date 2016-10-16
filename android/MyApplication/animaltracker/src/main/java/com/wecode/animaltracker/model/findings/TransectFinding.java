package com.wecode.animaltracker.model.findings;

import com.wecode.animaltracker.model.Persistable;

/**
 * Created by sziak on 10-Apr-16.
 */
public class TransectFinding extends Persistable {

    //FK
    private Long transectId;

    //FK
    private Long habitatId;

    private String species;

    private Double locationLatitude;

    private Double locationLongitude;

    private Double locationElevation;

    private String beforeAfterRecentSnow;

    public TransectFinding() {
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
        return "TransectFinding{" +
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
