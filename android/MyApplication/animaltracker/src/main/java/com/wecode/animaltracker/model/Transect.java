package com.wecode.animaltracker.model;

import android.location.Location;

import java.util.Date;

/**
 * Created by sziak on 10/28/2015.
 */
public class Transect {

    private Integer number;
    private Integer column;
    private Date startDateTime;
    private Date endDateTime;
    private Location startLocation;
    private Location endLocation;
    private String routeName;

    public Transect(Integer number, Integer column, Date startDateTime, Location startLocation, String routeName) {
        this.number = number;
        this.column = column;
        this.startDateTime = startDateTime;
        this.startLocation = startLocation;
        this.routeName = routeName;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
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
}
