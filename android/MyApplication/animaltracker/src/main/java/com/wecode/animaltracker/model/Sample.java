package com.wecode.animaltracker.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by SZIAK on 7/31/2016.
 */
@DatabaseTable(tableName = "SAMPLE")
public class Sample extends Persistable {

    public static final String SERIAL_NUMBER_COLUMN = "SAMPLE_NUMBER";

    public static final String TRANSECT_FINDING_ID_COLUMN = "TRANSECT_FINDING_ID";

    public static final String SAMPLE_SEQUENCE_NUMBER_COLUMN = "SAMPLE_SEQUENCE_NUMBER";

    public static final String SAMPLE_TYPE_COLUMN = "SAMPLE_TYPE";

    @DatabaseField(columnName = SERIAL_NUMBER_COLUMN, canBeNull = false)
    private String sampleNumber;

    @DatabaseField(columnName = TRANSECT_FINDING_ID_COLUMN, canBeNull = false)
    private Long transectFindingId;

    @DatabaseField(columnName = SAMPLE_SEQUENCE_NUMBER_COLUMN)
    private Integer sampleSequenceNumber;

    //EntityName
    @DatabaseField(columnName = SAMPLE_TYPE_COLUMN)
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


