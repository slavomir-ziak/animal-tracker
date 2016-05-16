package com.wecode.animaltracker.model;

import java.util.List;

/**
 * Created by sziak on 10-Apr-16.
 */
public class TransectFinding extends Persistable {

    private Long transectId;

    private String type;
    private String species;
    private String confidence;
    private Integer count;
    private Double locationLatitude;
    private Double locationLongitude;
    private String beforeAfterRecentSnow;

    private String fecesState;
    private String fecesPrey;

    private String footprintsFrontBack;
    private String footprintsDirection;
    private Integer footprintsLength;
    private Integer footprintsWidht;
    private Integer footprintsAge;
    private Integer footprintsStride;
    private Long habitatId;

    private List<Photo> photos;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getConfidence() {
        return confidence;
    }

    public void setConfidence(String confidence) {
        this.confidence = confidence;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getBeforeAfterRecentSnow() {
        return beforeAfterRecentSnow;
    }

    public void setBeforeAfterRecentSnow(String beforeAfterRecentSnow) {
        this.beforeAfterRecentSnow = beforeAfterRecentSnow;
    }

    public String getFecesState() {
        return fecesState;
    }

    public void setFecesState(String fecesState) {
        this.fecesState = fecesState;
    }

    public String getFecesPrey() {
        return fecesPrey;
    }

    public void setFecesPrey(String fecesPrey) {
        this.fecesPrey = fecesPrey;
    }

    public String getFootprintsFrontBack() {
        return footprintsFrontBack;
    }

    public void setFootprintsFrontBack(String footprintsFrontBack) {
        this.footprintsFrontBack = footprintsFrontBack;
    }

    public String getFootprintsDirection() {
        return footprintsDirection;
    }

    public void setFootprintsDirection(String footprintsDirection) {
        this.footprintsDirection = footprintsDirection;
    }

    public Integer getFootprintsLength() {
        return footprintsLength;
    }

    public void setFootprintsLength(Integer footprintsLength) {
        this.footprintsLength = footprintsLength;
    }

    public Integer getFootprintsWidht() {
        return footprintsWidht;
    }

    public void setFootprintsWidht(Integer footprintsWidht) {
        this.footprintsWidht = footprintsWidht;
    }

    public Integer getFootprintsAge() {
        return footprintsAge;
    }

    public void setFootprintsAge(Integer footprintsAge) {
        this.footprintsAge = footprintsAge;
    }

    public Integer getFootprintsStride() {
        return footprintsStride;
    }

    public void setFootprintsStride(Integer footprintsStride) {
        this.footprintsStride = footprintsStride;
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

    public void setHabitatId(Long habitatId) {
        this.habitatId = habitatId;
    }

    public Long getHabitatId() {
        return habitatId;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }
}
