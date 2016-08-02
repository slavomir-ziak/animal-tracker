package com.wecode.animaltracker.model;

/**
 * Created by SZIAK on 7/31/2016.
 */
public class Sample extends Persistable {

    private String sampleNumber;

    private Long transectFindingId;

    public Sample() {
    }

    public Sample(Long id, String sampleNumber, Long transectFindingId) {
        setId(id);
        this.sampleNumber = sampleNumber;
        this.transectFindingId = transectFindingId;
    }

    public Long getTransectFindingId() {
        return transectFindingId;
    }

    public void setTransectFindingId(Long transectFindingId) {
        this.transectFindingId = transectFindingId;
    }

    public String getSampleNumber() {
        return sampleNumber;
    }

    public void setSampleNumber(String sampleNumber) {
        this.sampleNumber = sampleNumber;
    }
}


