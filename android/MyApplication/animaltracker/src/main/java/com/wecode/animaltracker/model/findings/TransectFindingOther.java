package com.wecode.animaltracker.model.findings;

import com.wecode.animaltracker.model.Persistable;

/**
 * Created by SZIAK on 9/15/2016.
 */
public class TransectFindingOther extends Persistable {

    //FK
    private Long transectFindingId;

    private String evidence;

    private String observations;

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
                "} " + super.toString();
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAge() {
        return age;
    }
}
