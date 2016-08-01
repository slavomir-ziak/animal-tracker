package com.wecode.animaltracker.model;

import java.util.Date;

/**
 * Created by SZIAK on 7/31/2016.
 */
public class Sample extends Persistable {

    private String sampleNumber;

    private Long transectFindingDetailId;

    public Sample(Long id, String sampleNumber, Long transectFindingId) {
        setId(id);
        this.sampleNumber = sampleNumber;
        this.transectFindingDetailId = transectFindingId;
    }

    public Long getTransectFindingDetailId() {
        return transectFindingDetailId;
    }

    public void setTransectFindingDetailId(Long transectFindingDetailId) {
        this.transectFindingDetailId = transectFindingDetailId;
    }

    public String getSampleNumber() {
        return sampleNumber;
    }

    public void setSampleNumber(String sampleNumber) {
        this.sampleNumber = sampleNumber;
    }
}


