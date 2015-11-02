package com.wecode.animaltracker.model;

import android.location.Location;

import java.util.Date;

/**
 * Created by sziak on 10/28/2015.
 */
public class Transect extends Persistable {

    private Integer column;
    private Date startDateTime;
    private Date endDateTime;
    private Location startLocation;
    private Location endLocation;
    private String routeName;

    public Transect(Long id, Integer column, Date startDateTime, Location startLocation, String routeName) {
        setId(id);
        this.column = column;
        this.startDateTime = startDateTime;
        this.startLocation = startLocation;
        this.routeName = routeName;
    }
    public Transect(Long id, Integer column, Date startDateTime,Date endDateTime,
                    Location startLocation, Location endLocation, String routeName) {
        setId(id);
        this.column = column;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.routeName = routeName;
    }

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }

    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }

    public Location getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(Location startLocation) {
        this.startLocation = startLocation;
    }

    public Location getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(Location endLocation) {
        this.endLocation = endLocation;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    @Override
    public String toString() {
        return "Transect{" +
                "column=" + column+
                ", startDateTime=" + startDateTime +
                ", endDateTime=" + endDateTime +
                ", startLocation=" + startLocation +
                ", endLocation=" + endLocation +
                ", routeName='" + routeName + '\'' +
                '}';
    }
}
