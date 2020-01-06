package com.wecode.animaltracker.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by SZIAK on 9/25/2016.
 */
@DatabaseTable(tableName = "SETTINGS")
public class Settings extends Persistable {

    public static final String LOCATION_DECIMAL = "LOCATION_DECIMAL";

    public static final String LOCATION_DMS = "LOCATION_DMS";

    public static final String TRACKER_NAME_COLUMN = "TRACKER_NAME";

    public static final String LOCATION_FORMAT_COLUMN = "LOCATION_FORMAT";

    @DatabaseField(columnName = TRACKER_NAME_COLUMN)
    private String trackerName;

    @DatabaseField(columnName = LOCATION_FORMAT_COLUMN, canBeNull = false)
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
