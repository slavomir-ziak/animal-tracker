package com.wecode.animaltracker.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by sziak on 10/28/2015.
 */
@DatabaseTable(tableName = "TRANSECT")
public class Transect extends Persistable {

    public static final String SQUARE_COLUMN = "SQUARE";
    public static final String START_DATE_TIME_COLUMN = "START_DATE_TIME";
    public static final String END_DATE_TIME_COLUMN = "END_DATE_TIME";
    public static final String ROUTE_NAME_COLUMN = "ROUTE_NAME";
    public static final String LOCALISATION_COLUMN = "LOCALISATION";
    public static final String WEATHER_ID_COLUMN = "WEATHER_ID";
    public static final String START_LONGITUDE_COLUMN = "START_LONGITUDE";
    public static final String START_LATITUDE_COLUMN = "START_LATITUDE";
    public static final String START_ELEVATION_COLUMN = "START_ELEVATION";
    public static final String END_LONGITUDE_COLUMN = "END_LONGITUDE";
    public static final String END_LATITUDE_COLUMN = "END_LATITUDE";
    public static final String END_ELEVATION_COLUMN = "END_ELEVATION";
    public static final String ROOT_DIRECTORY_NAME_COLUMN = "ROOT_DIRECTORY_NAME";

    @DatabaseField(columnName = SQUARE_COLUMN)
    private Integer square;

    @DatabaseField(columnName = START_DATE_TIME_COLUMN)
    private Date startDateTime;

    @DatabaseField(columnName = END_DATE_TIME_COLUMN)
    private Date endDateTime;

    @DatabaseField(columnName = ROUTE_NAME_COLUMN)
    private String routeName;

    @DatabaseField(columnName = LOCALISATION_COLUMN)
    private String localisation;

    @DatabaseField(columnName = WEATHER_ID_COLUMN)
    private Long weatherId;

    @DatabaseField(columnName = START_LONGITUDE_COLUMN)
    private Double startLongitude;

    @DatabaseField(columnName = START_LATITUDE_COLUMN)
    private Double startLatitude;

    @DatabaseField(columnName = START_ELEVATION_COLUMN)
    private Double startElevation;

    @DatabaseField(columnName = END_LONGITUDE_COLUMN)
    private Double endLongitude;

    @DatabaseField(columnName = END_LATITUDE_COLUMN)
    private Double endLatitude;

    @DatabaseField(columnName = END_ELEVATION_COLUMN)
    private Double endElevation;

    @DatabaseField(columnName = ROOT_DIRECTORY_NAME_COLUMN)
    private String rootDirectoryName;

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
                "} " + super.toString();
    }

    public String getRootDirectoryName() {
        return rootDirectoryName;
    }

    public void setRootDirectoryName(String rootDirectoryName) {
        this.rootDirectoryName = rootDirectoryName;
    }


}
