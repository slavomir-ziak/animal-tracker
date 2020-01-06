package com.wecode.animaltracker.model.findings;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.wecode.animaltracker.model.Persistable;

import java.util.Locale;

/**
 * Created by SZIAK on 9/15/2016.
 */
@DatabaseTable(tableName = "TRANSECT_FINDING_FOOTPRINTS")
public class TransectFindingFootprints extends Persistable {

    public static final String TRANSECT_FINDING_ID_COLUMN = "TRANSECT_FINDING_ID";

    public static final String NUMBER_OF_ANIMALS_COLUMN = "NUMBER_OF_ANIMALS";

    public static final String FRONT_LENGTH_COLUMN = "FRONT_LENGTH";

    public static final String FRONT_WIDHT_COLUMN = "FRONT_WIDHT";

    public static final String BACK_LENGTH_COLUMN = "BACK_LENGTH";

    public static final String BACK_WIDHT_COLUMN = "BACK_WIDHT";

    public static final String DIRECTION_COLUMN = "DIRECTION";

    public static final String STRIDE_COLUMN = "STRIDE";

    public static final String CONFIDENCE_COLUMN = "CONFIDENCE";

    public static final String AGE_COLUMN = "AGE";

    public static final String SUBSTRACT_COLUMN = "SUBSTRACT";

    public static final String GROUP_VALUE_COLUMN = "GROUP_VALUE";
    
    // FK
    @DatabaseField(columnName = TRANSECT_FINDING_ID_COLUMN)
    private Long transectFindingId;

    @DatabaseField(columnName = NUMBER_OF_ANIMALS_COLUMN)
    private Integer numberOfAnimals;

    @DatabaseField(columnName = FRONT_LENGTH_COLUMN)
    private Float frontLength;

    @DatabaseField(columnName = FRONT_WIDHT_COLUMN)
    private Float frontWidht;

    @DatabaseField(columnName = BACK_LENGTH_COLUMN)
    private Float backLength;

    @DatabaseField(columnName = BACK_WIDHT_COLUMN)
    private Float backWidht;

    @DatabaseField(columnName = DIRECTION_COLUMN)
    private String direction;

    @DatabaseField(columnName = STRIDE_COLUMN)
    private Float stride;

    @DatabaseField(columnName = CONFIDENCE_COLUMN)
    private String confidence;

    @DatabaseField(columnName = AGE_COLUMN)
    private String age;

    @DatabaseField(columnName = SUBSTRACT_COLUMN)
    private String substract;

    @DatabaseField(columnName = GROUP_VALUE_COLUMN)
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
