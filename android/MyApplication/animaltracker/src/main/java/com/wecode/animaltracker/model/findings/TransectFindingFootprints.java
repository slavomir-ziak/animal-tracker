package com.wecode.animaltracker.model.findings;

import com.wecode.animaltracker.model.Persistable;

import java.util.Locale;

/**
 * Created by SZIAK on 9/15/2016.
 */
public class TransectFindingFootprints extends Persistable implements ITransectFinding  {

    // FK
    private Long transectFindingId;

    private Integer numberOfAnimals;

    private Float frontLength;

    private Float frontWidht;

    private Float backLength;

    private Float backWidht;

    private String direction;

    private Float stride;

    private String confidence;

    private String age;

    private String substract;

    private String groupValue;

    public TransectFindingFootprints() {
    }

    public Long getTransectFindingId() {
        return transectFindingId;
    }

    public void setTransectFindingId(Long transectFindingId) {
        this.transectFindingId = transectFindingId;
    }

    public Integer getNumberOfAnimals() {
        return numberOfAnimals;
    }

    public void setNumberOfAnimals(Integer numberOfAnimals) {
        this.numberOfAnimals = numberOfAnimals;
    }

    public Float getFrontLength() {
        return frontLength;
    }

    public void setFrontLength(Float frontLength) {
        this.frontLength = frontLength;
    }

    public Float getFrontWidht() {
        return frontWidht;
    }

    public void setFrontWidht(Float frontWidht) {
        this.frontWidht = frontWidht;
    }

    public Float getBackLength() {
        return backLength;
    }

    public void setBackLength(Float backLength) {
        this.backLength = backLength;
    }

    public Float getBackWidht() {
        return backWidht;
    }

    public void setBackWidht(Float backWidht) {
        this.backWidht = backWidht;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Float getStride() {
        return stride;
    }

    public void setStride(Float stride) {
        this.stride = stride;
    }

    public String getConfidence() {
        return confidence;
    }

    public void setConfidence(String confidence) {
        this.confidence = confidence;
    }

    public String getFrontLengthValue() {
        return getFrontLength() == null ? "" : getFrontLength().toString();
    }

    public String getFrontWidthValue() {
        return getFrontWidht() == null ? "" : getFrontWidht().toString();
    }

    public String getBackLengthValue() {
        return getBackLength() == null ? "" : getBackLength().toString();
    }

    public String getBackWidthValue() {
        return getBackWidht() == null ? "" : getBackWidht().toString();
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getSubstract() {
        return substract;
    }

    public void setSubstract(String substract) {
        this.substract = substract;
    }

    public String getNumberOfAnimalsValue() {
        return numberOfAnimals == null ? "" : numberOfAnimals.toString();
    }

    public String getFrontSizeFormatted() {
        return String.format(Locale.getDefault(), "%.1f x %.1f", getFrontLength(), getFrontWidht());
    }

    public String getBackSizeFormatted() {
        return String.format(Locale.getDefault(), "%.1f x %.1f", getBackLength(), getBackWidht());
    }

    @Override
    public String toString() {
        return "TransectFindingFootprints{" +
                "transectFindingId=" + transectFindingId +
                ", numberOfAnimals=" + numberOfAnimals +
                ", frontLength=" + frontLength +
                ", frontWidht=" + frontWidht +
                ", backLength=" + backLength +
                ", backWidht=" + backWidht +
                ", direction='" + direction + '\'' +
                ", stride=" + stride +
                ", confidence='" + confidence + '\'' +
                ", age='" + age + '\'' +
                ", substract='" + substract + '\'' +
                ", groupValue='" + groupValue + '\'' +
                "} " + super.toString();
    }

    public String getGroupValue() {
        return groupValue;
    }

    public void setGroupValue(String groupValue) {
        this.groupValue = groupValue;
    }
}
