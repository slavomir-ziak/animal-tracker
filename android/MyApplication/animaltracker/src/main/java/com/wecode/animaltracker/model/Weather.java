package com.wecode.animaltracker.model;

/**
 * Created by sziak on 10-Apr-16.
 */
public class Weather extends Persistable {

    private Integer sunshineIntensity;
    private Integer windIntensity;
    private Integer rainIntensity;
    private Integer humidity;
    private Integer visibility;
    private Integer showDepth;
    private Integer newSnowDepth;
    private Integer lastTimeSnowedRained;

    public Weather() {
    }

    public Weather(Long id, Integer sunshineIntensity, Integer windIntensity, Integer rainIntensity, Integer humidity, Integer visibility, Integer showDepth, Integer newSnowDepth, Integer lastTimeSnowedRained) {
        this.setId(id);
        this.sunshineIntensity = sunshineIntensity;
        this.windIntensity = windIntensity;
        this.rainIntensity = rainIntensity;
        this.humidity = humidity;
        this.visibility = visibility;
        this.showDepth = showDepth;
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

    public Integer getShowDepth() {
        return showDepth;
    }

    public void setShowDepth(Integer showDepth) {
        this.showDepth = showDepth;
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
}
