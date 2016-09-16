package com.wecode.animaltracker.model.findings;

import com.wecode.animaltracker.model.Persistable;

/**
 * Created by SZIAK on 9/15/2016.
 */
public class TransectFindingFeces extends Persistable {

    //FK
    private Long transectFindingId;

    private String fecesState;

    private String fecesPrey;

    private String confidence;

    private String age;

    public TransectFindingFeces() {
    }

    public Long getTransectFindingId() {
        return transectFindingId;
    }

    public void setTransectFindingId(Long transectFindingId) {
        this.transectFindingId = transectFindingId;
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

    public String getConfidence() {
        return confidence;
    }

    public void setConfidence(String confidence) {
        this.confidence = confidence;
    }

    @Override
    public String toString() {
        return "TransectFindingFeces{" +
                "transectFindingId=" + transectFindingId +
                ", fecesState='" + fecesState + '\'' +
                ", fecesPrey='" + fecesPrey + '\'' +
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
