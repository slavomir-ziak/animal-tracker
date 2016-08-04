package com.wecode.animaltracker.model;

import android.location.Location;

import java.util.Date;

/**
 * Created by sziak on 10/28/2015.
 */
public class Transect extends Persistable {

    private Long habitatId;
    private Integer column;
    private Date startDateTime;
    private Date endDateTime;
    private String routeName;
    private Long weatherId;

    private Double startLongitude;
    private Double startLatitude;
    private Double endLongitude;
    private Double endLatitude;

    public Transect() {
    }

    public Transect(Long id, Integer column, Date startDateTime, String routeName) {
        setId(id);
        this.column = column;
        this.startDateTime = startDateTime;
        this.routeName = routeName;
    }
    public Transect(Long id, Integer column, Date startDateTime,Date endDateTime,
                    Location startLocation, Location endLocation, String routeName) {
        setId(id);
        this.column = column;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
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
                ", routeName='" + routeName + '\'' +
                '}';
    }

    public void setHabitatId(Long habitatId) {
        this.habitatId = habitatId;
    }

    public Long getHabitatId() {
        return habitatId;
    }

    public Long getWatherId() {
        return weatherId;
    }

    public void setWeatherId(Long weatherId) {
        this.weatherId = weatherId;
    }

    public void setStartLongitude(double startLongitude) {
        this.startLongitude = startLongitude;
    }

    public Double getStartLongitude() {
        return startLongitude;
    }

    public void setStartLatitude(Double startLatitude) {
        this.startLatitude = startLatitude;
    }

    public Double getStartLatitude() {
        return startLatitude;
    }

    public void setEndLongitude(Double endLongitude) {
        this.endLongitude = endLongitude;
    }

    public void setEndLatitude(Double endLatitude) {
        this.endLatitude = endLatitude;
    }

    public Long getWeatherId() {
        return weatherId;
    }

    public void setStartLongitude(Double startLongitude) {
        this.startLongitude = startLongitude;
    }

    public Double getEndLongitude() {
        return endLongitude;
    }

    public Double getEndLatitude() {
        return endLatitude;
    }
}
