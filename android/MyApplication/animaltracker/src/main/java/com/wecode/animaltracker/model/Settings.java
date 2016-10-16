package com.wecode.animaltracker.model;

/**
 * Created by SZIAK on 9/25/2016.
 */
public class Settings extends Persistable {

    public static final String LOCATION_DECIMAL = "LOCATION_DECIMAL";

    public static final String LOCATION_DMS = "LOCATION_DMS";

    private String trackerName;

    private String locationFormat;

    public String getTrackerName() {
        return trackerName;
    }

    public void setTrackerName(String trackerName) {
        this.trackerName = trackerName;
    }

    public String getLocationFormat() {
        return locationFormat;
    }

    public void setLocationFormat(String locationFormat) {
        this.locationFormat = locationFormat;
    }

    @Override
    public String toString() {
        return "Settings{" +
                "trackerName='" + trackerName + '\'' +
                ", locationFormat='" + locationFormat + '\'' +
                "} " + super.toString();
    }

    public boolean isLocationDecimal() {
        return locationFormat != null && locationFormat.equals(LOCATION_DECIMAL);
    }

    public boolean isLocationDMS() {
        return locationFormat != null && locationFormat.equals(LOCATION_DMS);
    }
}
