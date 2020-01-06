package com.wecode.animaltracker.model.findings;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.wecode.animaltracker.model.Persistable;

/**
 * Created by SZIAK on 9/15/2016.
 */
@DatabaseTable(tableName = "TRANSECT_FINDING_OTHER")
public class TransectFindingOther extends Persistable {
    
    public static final String TRANSECT_FINDING_ID_COLUMN = "TRANSECT_FINDING_ID";

    public static final String EVIDENCE_COLUMN = "EVIDENCE";

    public static final String OBSERVATIONS_COLUMN = "OBSERVATIONS";

    public static final String CONFIDENCE_COLUMN = "CONFIDENCE";

    public static final String AGE_COLUMN = "AGE";

    public static final String SUBSTRACT_COLUMN = "SUBSTRACT";
    
    //FK
    @DatabaseField(columnName = TRANSECT_FINDING_ID_COLUMN)
    private Long transectFindingId;

    @DatabaseField(columnName = EVIDENCE_COLUMN)
    private String evidence;

    @DatabaseField(columnName = OBSERVATIONS_COLUMN)
    private String observations;

    @DatabaseField(columnName = CONFIDENCE_COLUMN)
    private String confidence;

    @DatabaseField(columnName = AGE_COLUMN)
    private String age;

    @DatabaseField(columnName = SUBSTRACT_COLUMN)
    private String substract;

    public TransectFindingOther() {
    }

    public Long getTransectFindingId() {
        return transectFindingId;
    }

    public void setTransectFindingId(Long transectFindingId) {
        this.transectFindingId = transectFindingId;
    }

    public String getEvidence() {
        return evidence;
    }

    public void setEvidence(String evidence) {
        this.evidence = evidence;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public String getConfidence() {
        return confidence;
    }

    public void setConfidence(String confidence) {
        this.confidence = confidence;
    }

    @Override
    public String toString() {
        return "TransectFindingOther{" +
                "transectFindingId=" + transectFindingId +
                ", evidence='" + evidence + '\'' +
                ", observations='" + observations + '\'' +
                ", confidence='" + confidence + '\'' +
                ", age='" + age + '\'' +
                ", substract='" + substract + '\'' +
                "} " + super.toString();
    }

    public String getSubstract() {
        return substract;
    }

    public void setSubstract(String substract) {
        this.substract = substract;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAge() {
        return age;
    }
}
