package com.wecode.animaltracker.export;

import android.annotation.SuppressLint;

import com.wecode.animaltracker.model.Habitat;
import com.wecode.animaltracker.util.StringUtils;

import java.util.Locale;

/**
 * Created by SZIAK on 9/20/2016.
 */
public class TransectReportRow {

    private Integer number;
    private String specie;
    private Double elevation;
    private Double latitude;
    private Double longitude;
    private String habitat;

    private Integer footprintsNumberOfAnimlas;
    private String footprintsSubstract;
    private String footprintsDirection;
    private Float footprintsStride;
    private String footprintsFrontSize;
    private String footprintsBackSize;

    private String fecesState;
    private String fecesPrey;
    private String fecesSubstract;

    private String urineLocation;

    private String otherEvidence;
    private String OtherObservations;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getSpecie() {
        return specie;
    }

    public void setSpecie(String specie) {
        this.specie = specie;
    }

    public Double getElevation() {
        return elevation;
    }

    public String getElevationValue() {
        return elevation == null ? "" : String.format(Locale.US, "%.5f", elevation);
    }

    public void setElevation(Double elevation) {
        this.elevation = elevation;
    }

    public Double getLatitude() {
        return latitude;
    }

    public String getLatitudeValue() {
        return String.format(Locale.US, "%.5f", latitude);
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getLongitudeValue() {
        return String.format(Locale.US, "%.5f", longitude);
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getHabitat() {
        return habitat;
    }

    public void setHabitat(Habitat habitat) {
        StringBuilder sb = new StringBuilder();

        if (StringUtils.isNotEmpty(habitat.getType())) {
            sb.append(habitat.getType()).append(", ");
        }
        if (StringUtils.isNotEmpty(habitat.getTrack())) {
            sb.append(habitat.getTrack()).append(", ");
        }

        if (habitat.isForest()) {
            if (StringUtils.isNotEmpty(habitat.getForestType())) {
                sb.append(habitat.getForestType()).append(", ");
            }
            if (StringUtils.isNotEmpty(habitat.getForestAge())) {
                sb.append(habitat.getForestAge()).append(", ");
            }
            if (StringUtils.isNotEmpty(habitat.getTreeType())) {
                sb.append(habitat.getTreeType()).append(", ");
            }
        }

        this.habitat = sb.toString();
    }

    public Integer getFootprintsNumberOfAnimlas() {
        return footprintsNumberOfAnimlas;
    }

    public void setFootprintsNumberOfAnimlas(Integer footprintsNumberOfAnimlas) {
        this.footprintsNumberOfAnimlas = footprintsNumberOfAnimlas;
    }

    public String getFootprintsSubstract() {
        return footprintsSubstract;
    }

    public void setFootprintsSubstract(String footprintsSubstract) {
        this.footprintsSubstract = footprintsSubstract;
    }

    public String getFootprintsDirection() {
        return footprintsDirection;
    }

    public void setFootprintsDirection(String footprintsDirection) {
        this.footprintsDirection = footprintsDirection;
    }

    public Float getFootprintsStride() {
        return footprintsStride;
    }

    @SuppressLint("DefaultLocale")
    public String getFootprintsStrideValue() {
        return String.format("%.1f", footprintsStride);
    }

    public void setFootprintsStride(Float footprintsStride) {
        this.footprintsStride = footprintsStride;
    }

    public String getFootprintsFrontSize() {
        return footprintsFrontSize;
    }

    public void setFootprintsFrontSize(String footprintsFrontSize) {
        this.footprintsFrontSize = footprintsFrontSize;
    }

    public String getFootprintsBackSize() {
        return footprintsBackSize;
    }

    public void setFootprintsBackSize(String footprintsBackSize) {
        this.footprintsBackSize = footprintsBackSize;
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

    public String getFecesSubstract() {
        return fecesSubstract;
    }

    public void setFecesSubstract(String fecesSubstract) {
        this.fecesSubstract = fecesSubstract;
    }

    public String getUrineLocation() {
        return urineLocation;
    }

    public void setUrineLocation(String urineLocation) {
        this.urineLocation = urineLocation;
    }

    public String getOtherEvidence() {
        return otherEvidence;
    }

    public void setOtherEvidence(String otherEvidence) {
        this.otherEvidence = otherEvidence;
    }

    public String getOtherObservations() {
        return OtherObservations;
    }

    public void setOtherObservations(String otherObservations) {
        OtherObservations = otherObservations;
    }
}
