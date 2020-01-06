package com.wecode.animaltracker.model.findings;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.wecode.animaltracker.model.Persistable;

/**
 * Created by SZIAK on 9/15/2016.
 */
@DatabaseTable(tableName = "TRANSECT_FINDING_FECES")
public class TransectFindingFeces extends Persistable {

    public static final String TRANSECT_FINDING_ID_COLUMN = "TRANSECT_FINDING_ID";

    public static final String STATE_COLUMN = "STATE";

    public static final String PREY_COLUMN = "PREY";

    public static final String CONFIDENCE_COLUMN = "CONFIDENCE";

    public static final String AGE_COLUMN = "AGE";

    public static final String SUBSTRACT_COLUMN = "SUBSTRACT";

    public static final String COLLECTED_COLUMN = "COLLECTED";

    //FK
    @DatabaseField(columnName = TRANSECT_FINDING_ID_COLUMN)
    private Long transectFindingId;

    @DatabaseField(columnName = STATE_COLUMN)
    private String state;

    @DatabaseField(columnName = PREY_COLUMN)
    private String prey;

    @DatabaseField(columnName = CONFIDENCE_COLUMN)
    private String confidence;

    @DatabaseField(columnName = AGE_COLUMN)
    private String age;

    @DatabaseField(columnName = SUBSTRACT_COLUMN)
    private String substract;

    @DatabaseField(columnName = COLLECTED_COLUMN)
    private Boolean collected;

    public TransectFindingFeces() {
    }

    public Long getTransectFindingId() {
        return transectFindingId;
    }

    public void setTransectFindingId(Long transectFindingId) {
        this.transectFindingId = transectFindingId;
    }

    public String getSubstract() {
        return substract;
    }

    public void setSubstract(String substract) {
        this.substract = substract;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPrey() {
        return prey;
    }

    public void setPrey(String prey) {
        this.prey = prey;
    }

    public String getConfidence() {
        return confidence;
    }

    public void setConfidence(String confidence) {
        this.confidence = confidence;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "TransectFindingFeces{" +
                "transectFindingId=" + transectFindingId +
                ", state='" + state + '\'' +
                ", prey='" + prey + '\'' +
                ", confidence='" + confidence + '\'' +
                ", age='" + age + '\'' +
                ", substract='" + substract + '\'' +
                ", collected=" + collected +
                "} " + super.toString();
    }

    public Boolean isCollected() {
        return collected;
    }

    public void setCollected(Boolean collected) {
        this.collected = collected;
    }
}
