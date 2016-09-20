package com.wecode.animaltracker.model.findings;

import com.wecode.animaltracker.model.Persistable;

/**
 * Created by SZIAK on 9/15/2016.
 */
public class TransectFindingFeces extends Persistable {

    //FK
    private Long transectFindingId;

    private String state;

    private String prey;

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

    @Override
    public String toString() {
        return "TransectFindingFeces{" +
                "transectFindingId=" + transectFindingId +
                ", state='" + state + '\'' +
                ", prey='" + prey + '\'' +
                ", confidence='" + confidence + '\'' +
                ", age=" + age +
                "} " + super.toString();
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAge() {
        return age;
    }
}
