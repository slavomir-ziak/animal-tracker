package com.wecode.animaltracker.model;

import com.wecode.animaltracker.service.TransectFindingSiteDataService;

import java.util.Date;
import java.util.List;

/**
 * Created by sziak on 10/28/2015.
 */
public class Transect extends Persistable {

    private Integer square;
    private Date startDateTime;
    private Date endDateTime;
    private String routeName;
    private String localisation;
    private Long weatherId;

    private Double startLongitude;
    private Double startLatitude;
    private Double startElevation;

    private Double endLongitude;
    private Double endLatitude;
    private Double endElevation;

    private String rootDirectoryName;

    private List<TransectFindingSite> findings;

    public Transect() {
    }

    public Transect(Long id, Integer square, Date startDateTime, String routeName) {
        setId(id);
        this.square = square;
        this.startDateTime = startDateTime;
        this.routeName = routeName;
    }

    public Integer getSquare() {
        return square;
    }

    public void setSquare(Integer square) {
        this.square = square;
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

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public Double getStartElevation() {
        return startElevation;
    }

    public void setStartElevation(Double startElevation) {
        this.startElevation = startElevation;
    }

    public Double getEndElevation() {
        return endElevation;
    }

    public void setEndElevation(Double endElevation) {
        this.endElevation = endElevation;
    }

    public List<TransectFindingSite> getFindingSites() {
        if (findings == null) {
            findings = TransectFindingSiteDataService.getInstance().findByTransectId(getId());
        }
        return findings;
    }

    public String getColumn(String defaultValue) {
        return square != null ? square.toString() : defaultValue;
    }

    @Override
    public String toString() {
        return "Transect{" +
                ", square=" + square +
                ", startDateTime=" + startDateTime +
                ", endDateTime=" + endDateTime +
                ", routeName='" + routeName + '\'' +
                ", localisation='" + localisation + '\'' +
                ", weatherId=" + weatherId +
                ", startLongitude=" + startLongitude +
                ", startLatitude=" + startLatitude +
                ", startElevation=" + startElevation +
                ", endLongitude=" + endLongitude +
                ", endLatitude=" + endLatitude +
                ", endElevation=" + endElevation +
                ", rootDirectoryName='" + rootDirectoryName + '\'' +
                ", findings=" + findings +
                "} " + super.toString();
    }

    public String getRootDirectoryName() {
        return rootDirectoryName;
    }

    public void setRootDirectoryName(String rootDirectoryName) {
        this.rootDirectoryName = rootDirectoryName;
    }


}
