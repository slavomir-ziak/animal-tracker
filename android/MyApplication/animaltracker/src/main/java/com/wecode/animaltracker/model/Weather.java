package com.wecode.animaltracker.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by sziak on 10-Apr-16.
 */
@DatabaseTable(tableName = "WEATHER")
public class Weather extends Persistable {

    public static final String SUNSHINE_INTENSITY_COLUMN = "SUNSHINE_INTENSITY";
    public static final String WIND_INTENSITY_COLUMN = "WIND_INTENSITY";
    public static final String RAIN_INTENSITY_COLUMN = "RAIN_INTENSITY";
    public static final String HUMIDITY_COLUMN = "HUMIDITY";
    public static final String VISIBILITY_COLUMN = "VISIBILITY";
    public static final String SNOW_DEPTH_COLUMN = "SNOW_DEPTH";
    public static final String NEW_SNOW_DEPTH_COLUMN = "NEW_SNOW_DEPTH";
    public static final String LAST_TIME_SNOWED_RAINED_COLUMN = "LAST_TIME_SNOWED_RAINED";

    @DatabaseField(columnName = SUNSHINE_INTENSITY_COLUMN)
    private Integer sunshineIntensity;

    @DatabaseField(columnName = WIND_INTENSITY_COLUMN)
    private Integer windIntensity;

    @DatabaseField(columnName = RAIN_INTENSITY_COLUMN)
    private Integer rainIntensity;

    @DatabaseField(columnName = HUMIDITY_COLUMN)
    private Integer humidity;

    @DatabaseField(columnName = VISIBILITY_COLUMN)
    private Integer visibility;

    @DatabaseField(columnName = SNOW_DEPTH_COLUMN)
    private Integer snowDepth;

    @DatabaseField(columnName = NEW_SNOW_DEPTH_COLUMN)
    private Integer newSnowDepth;

    @DatabaseField(columnName = LAST_TIME_SNOWED_RAINED_COLUMN)
    private Integer lastTimeSnowedRained;

    public Weather() {
    }

    public Weather(Long id, Integer sunshineIntensity, Integer windIntensity, Integer rainIntensity, Integer humidity, Integer visibility, Integer snowDepth, Integer newSnowDepth, Integer lastTimeSnowedRained) {
        this.setId(id);
        this.sunshineIntensity = sunshineIntensity;
        this.windIntensity = windIntensity;
        this.rainIntensity = rainIntensity;
        this.humidity = humidity;
        this.visibility = visibility;
        this.snowDepth = snowDepth;
        this.newSnowDepth = newSnowDepth;
        this.lastTimeSnowedRained = lastTimeSnowedRained;
    }

    public Integer getSunshineIntensity() {
        return sunshineIntensity;
    }

    public void setSunshineIntensity(Integer sunshineIntensity) {
        this.sunshineIntensity = sunshineIntensity;
    }

    public Integer getWindIntensity() {
        return windIntensity;
    }

    public void setWindIntensity(Integer windIntensity) {
        this.windIntensity = windIntensity;
    }

    public Integer getRainIntensity() {
        return rainIntensity;
    }

    public void setRainIntensity(Integer rainIntensity) {
        this.rainIntensity = rainIntensity;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    public Integer getSnowDepth() {
        return snowDepth;
    }

    public void setSnowDepth(Integer snowDepth) {
        this.snowDepth = snowDepth;
    }

    public Integer getNewSnowDepth() {
        return newSnowDepth;
    }

    public void setNewSnowDepth(Integer newSnowDepth) {
        this.newSnowDepth = newSnowDepth;
    }

    public Integer getLastTimeSnowedRained() {
        return lastTimeSnowedRained;
    }

    public void setLastTimeSnowedRained(Integer lastTimeSnowedRained) {
        this.lastTimeSnowedRained = lastTimeSnowedRained;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "sunshineIntensity=" + sunshineIntensity +
                ", windIntensity=" + windIntensity +
                ", rainIntensity=" + rainIntensity +
                ", humidity=" + humidity +
                ", visibility=" + visibility +
                ", snowDepth=" + snowDepth +
                ", newSnowDepth=" + newSnowDepth +
                ", lastTimeSnowedRained=" + lastTimeSnowedRained +
                "} " + super.toString();
    }
}
