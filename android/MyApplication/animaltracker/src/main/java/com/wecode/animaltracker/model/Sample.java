package com.wecode.animaltracker.model;

import java.util.Date;

/**
 * Created by SZIAK on 7/31/2016.
 */
public class Sample extends Persistable {

    private String sampleNumber;

    private Long transectFindingId;

    private Integer sampleSequenceNumber;

    public Sample() {
    }

    public Sample(String sampleNumber, Long transectFindingId, Integer sampleSequenceNumber) {
        this.sampleSequenceNumber = sampleSequenceNumber;
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

    @Override
    public String toString() {
        return "Sample{" +
                "sampleNumber='" + sampleNumber + '\'' +
                ", transectFindingId=" + transectFindingId +
                ", sampleSequenceNumber=" + sampleSequenceNumber +
                "} " + super.toString();
    }

    public Integer getSampleSequenceNumber() {
        return sampleSequenceNumber;
    }

    public void setSampleSequenceNumber(Integer sampleSequenceNumber) {
        this.sampleSequenceNumber = sampleSequenceNumber;
    }
}


