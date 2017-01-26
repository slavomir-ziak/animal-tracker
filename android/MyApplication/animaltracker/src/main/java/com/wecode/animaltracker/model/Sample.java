package com.wecode.animaltracker.model;

import com.orm.dsl.NotNull;

import java.util.Date;

/**
 * Created by SZIAK on 7/31/2016.
 */
public class Sample extends Persistable {

    @NotNull
    private String sampleNumber;

    @NotNull
    private Long transectFindingId;

    @NotNull
    private Integer sampleSequenceNumber;

    //EntityName
    private String sampleType;

    public Sample() {
    }

    public Sample(String sampleNumber, Long transectFindingId, Integer sampleSequenceNumber, EntityName sampleType) {
        this.sampleType = sampleType.toString();
        this.sampleSequenceNumber = sampleSequenceNumber;
        this.sampleNumber = sampleNumber;
        this.transectFindingId = transectFindingId;
    }

    @Override
    public String toString() {
        return "Sample{" +
                "sampleNumber='" + sampleNumber + '\'' +
                ", transectFindingId=" + transectFindingId +
                ", sampleSequenceNumber=" + sampleSequenceNumber +
                ", sampleType='" + sampleType + '\'' +
                "} " + super.toString();
    }

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
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

    public Integer getSampleSequenceNumber() {
        return sampleSequenceNumber;
    }

    public void setSampleSequenceNumber(Integer sampleSequenceNumber) {
        this.sampleSequenceNumber = sampleSequenceNumber;
    }
}


