package com.wecode.animaltracker.model.findings;

import com.wecode.animaltracker.model.Persistable;

/**
 * Created by SZIAK on 9/15/2016.
 */
public class TransectFindingOther extends Persistable {

    //FK
    private Long transectFindingId;

    private String otherEvidence;

    private String otherObservations;

    private String confidence;

    private String age;

    public TransectFindingOther() {
    }

    public Long getTransectFindingId() {
        return transectFindingId;
    }

    public void setTransectFindingId(Long transectFindingId) {
        this.transectFindingId = transectFindingId;
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
                ", otherEvidence='" + otherEvidence + '\'' +
                ", otherObservations='" + otherObservations + '\'' +
                ", confidence='" + confidence + '\'' +
                ", age=" + age +
                '}';
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAge() {
        return age;
    }
}
