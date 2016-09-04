package com.wecode.animaltracker.model;

/**
 * Created by sziak on 10-Apr-16.
 */
public class TransectFinding extends Persistable {

    private Long transectId;
    private Long habitatId;

    private String species;
    private Integer numberOfAnimals;
    private Double locationLatitude;
    private Double locationLongitude;
    private String beforeAfterRecentSnow;
    private String confidence;

    private String fecesState;
    private String fecesPrey;

    private String footprintsDirection;
    private Float footprintsFrontLength;
    private Float footprintsFrontWidht;
    private Float footprintsBackLength;
    private Float footprintsBackWidht;
    private Float footprintsAge;
    private Float footprintsStride;

    private String urineLocation;

    private String otherEvidence;
    private String otherObservations;

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

    public Integer getNumberOfAnimals() {
        return numberOfAnimals;
    }

    public void setNumberOfAnimals(Integer numberOfAnimals) {
        this.numberOfAnimals = numberOfAnimals;
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

    public String getFootprintsDirection() {
        return footprintsDirection;
    }

    public void setFootprintsDirection(String footprintsDirection) {
        this.footprintsDirection = footprintsDirection;
    }

    public Float getFootprintsFrontLength() {
        return footprintsFrontLength;
    }

    public void setFootprintsFrontLength(Float footprintsFrontLength) {
        this.footprintsFrontLength = footprintsFrontLength;
    }

    public Float getFootprintsFrontWidht() {
        return footprintsFrontWidht;
    }

    public void setFootprintsFrontWidht(Float footprintsFrontWidht) {
        this.footprintsFrontWidht = footprintsFrontWidht;
    }

    public Float getFootprintsBackLength() {
        return footprintsBackLength;
    }

    public void setFootprintsBackLength(Float footprintsBackLength) {
        this.footprintsBackLength = footprintsBackLength;
    }

    public Float getFootprintsBackWidht() {
        return footprintsBackWidht;
    }

    public void setFootprintsBackWidht(Float footprintsBackWidht) {
        this.footprintsBackWidht = footprintsBackWidht;
    }

    public Float getFootprintsAge() {
        return footprintsAge;
    }

    public void setFootprintsAge(Float footprintsAge) {
        this.footprintsAge = footprintsAge;
    }

    public Float getFootprintsStride() {
        return footprintsStride;
    }

    public void setFootprintsStride(Float footprintsStride) {
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

    public String getOtherEvidence() {
        return otherEvidence;
    }

    public void setOtherEvidence(String otherEvidence) {
        this.otherEvidence = otherEvidence;
    }

    public String getOtherObservations() {
        return otherObservations;
    }

    public void setOtherObservations(String otherObservations) {
        this.otherObservations = otherObservations;
    }

    @Override
    public String toString() {
        return "TransectFinding{" +
                "transectId=" + transectId +
                ", habitatId=" + habitatId +
                ", species='" + species + '\'' +
                ", numberOfAnimals=" + numberOfAnimals +
                ", urineLocation='" + urineLocation + '\'' +
                ", locationLatitude=" + locationLatitude +
                ", locationLongitude=" + locationLongitude +
                ", beforeAfterRecentSnow='" + beforeAfterRecentSnow + '\'' +
                ", confidence='" + confidence + '\'' +
                ", fecesState='" + fecesState + '\'' +
                ", fecesPrey='" + fecesPrey + '\'' +
                ", footprintsDirection='" + footprintsDirection + '\'' +
                ", footprintsFrontLength=" + footprintsFrontLength +
                ", footprintsFrontWidht=" + footprintsFrontWidht +
                ", footprintsBackLength=" + footprintsBackLength +
                ", footprintsBackWidht=" + footprintsBackWidht +
                ", footprintsAge=" + footprintsAge +
                ", footprintsStride=" + footprintsStride +
                ", otherEvidence='" + otherEvidence + '\'' +
                ", otherObservations='" + otherObservations + '\'' +
                '}';
    }

    public String getUrineLocation() {
        return urineLocation;
    }

    public void setUrineLocation(String urineLocation) {
        this.urineLocation = urineLocation;
    }

    public String getFootprintsFrontLengthValue() {
        return getFootprintsFrontLength() == null ? "" : getFootprintsFrontLength().toString();
    }

    public String getFootprintsFrontWidthValue() {
        return getFootprintsFrontWidht() == null ? "" : getFootprintsFrontWidht().toString();
    }

    public String getFootprintsBackLengthValue() {
        return getFootprintsBackLength() == null ? "" : getFootprintsBackLength().toString();
    }

    public String getFootprintsBackWidthValue() {
        return getFootprintsBackWidht() == null ? "" : getFootprintsBackWidht().toString();
    }

    public boolean hasFecesData() {
        return (fecesPrey != null && fecesPrey.length() > 0) || (fecesState != null && fecesState.length() > 0);
    }

    public boolean hasOtherData() {
        return (otherEvidence != null && otherEvidence.length() > 0) ||
                (otherObservations != null && otherObservations.length() > 0) ||
                (footprintsAge != null) ||
                (beforeAfterRecentSnow != null && beforeAfterRecentSnow.length() > 0) ||
                (confidence != null && confidence.length() > 0);

    }
}
