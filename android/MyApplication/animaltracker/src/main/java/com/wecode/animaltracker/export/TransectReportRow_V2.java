package com.wecode.animaltracker.export;

import com.wecode.animaltracker.model.Habitat;
import com.wecode.animaltracker.model.Persistable;
import com.wecode.animaltracker.model.Photo;
import com.wecode.animaltracker.model.Transect;
import com.wecode.animaltracker.model.TransectFindingSite;
import com.wecode.animaltracker.model.findings.ITransectFinding;
import com.wecode.animaltracker.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by SZIAK on 9/20/2016.
 */
class TransectReportRow_V2 {

    private Long id;
    private String name;
    private String description;
    private Integer numberOfIndividuals;
    private Long findingId;
    private String findingType;
    private String sample = "?";
    private String symbol = "?";
    private Integer squareNo;
    private Date created;
    private Double elevation;
    private Double latitude;
    private Double longitude;
    private String confidence;
    private String snow;
    private String snowType = "?";
    private String snowCover = "?";
    private String habitatType;
    private String forestType;
    private String forestAge;
    private String treeType;
    private String track;
    private List<String> photos;
    private String sample2 = "?";
    private String comment;

    TransectReportRow_V2(String findingType,
                         TransectFindingSite transectFindingSite,
                         Transect transect,
                         ITransectFinding iTransectFinding,
                         Habitat habitat,
                         List<Photo> photos) {
        setFindingType(findingType);
        setSquareNo(transect.getSquare());
        setElevation(transectFindingSite.getLocationElevation());
        setLatitude(transectFindingSite.getLocationLatitude());
        setLongitude(transectFindingSite.getLocationLongitude());
        setName(transect.getLocalisation() + " - " + transect.getRouteName());
        setConfidence(iTransectFinding.getConfidence());
        setComment(((Persistable)iTransectFinding).getComment());
        setConfidence(iTransectFinding.getConfidence());
        setCreated(((Persistable)iTransectFinding).getCreated());
        setFindingId(((Persistable)iTransectFinding).getId());
        setComment(((Persistable)iTransectFinding).getComment());
        String substract = iTransectFinding.getSubstract();
        if (substract != null && substract.equalsIgnoreCase("snow")) {
            setSnow("1");
        } else {
            setSnow("0");
        }
        if (habitat != null) {
            String habitatType = habitat.getType();
            setHabitatType(habitatType);
            setTrack(habitat.getTrack());
            if (habitatType != null && habitatType.equalsIgnoreCase("forest")) {
                setForestAge(habitat.getForestAge());
                setForestType(habitat.getForestType());
                setTreeType(habitat.getTreeType());
            }
        }
        setPhotos(photos);
    }

    public String getFindingType() {
        return findingType;
    }

    private void setFindingType(String findingType) {
        this.findingType = findingType != null ? findingType.toUpperCase() : "";
    }

    public Long getFindingId() {
        return findingId;
    }

    public void setFindingId(Long findingId) {
        this.findingId = findingId;
    }

    Date getCreated() {
        return created;
    }

    void setCreated(Date created) {
        this.created = created;
    }

    Double getElevation() {
        return elevation;
    }

    String getElevationValue() {
        return elevation  == null ? "" : String.format(Locale.getDefault(), "%.5f", elevation );
    }

    void setElevation(Double elevation) {
        this.elevation = elevation;
    }

    Double getLatitude() {
        return latitude;
    }

    String getLatitudeValue() {
        return latitude  == null ? "" : String.format(Locale.getDefault(), "%.5f", latitude );
    }

    void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    Double getLongitude() {
        return longitude;
    }

    String getLongitudeValue() {
        return longitude  == null ? "" : String.format(Locale.getDefault(), "%.5f", longitude );
    }

    void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNumberOfIndividuals() {
        return numberOfIndividuals;
    }

    public void setNumberOfIndividuals(Integer numberOfIndividuals) {
        this.numberOfIndividuals = numberOfIndividuals;
    }

    public String getSample() {
        return sample;
    }

    public void setSample(String sample) {
        this.sample = sample;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Integer getSquareNo() {
        return squareNo;
    }

    public void setSquareNo(Integer squareNo) {
        this.squareNo = squareNo;
    }

    public String getConfidence() {
        return confidence;
    }

    public void setConfidence(String confidence) {
        this.confidence = confidence;
    }

    public String getSnow() {
        return snow;
    }

    public void setSnow(String snow) {
        this.snow = snow;
    }

    public String getSnowType() {
        return snowType;
    }

    public void setSnowType(String snowType) {
        this.snowType = snowType;
    }

    public String getSnowCover() {
        return snowCover;
    }

    public void setSnowCover(String snowCover) {
        this.snowCover = snowCover;
    }

    public String getHabitatType() {
        return habitatType;
    }

    public void setHabitatType(String habitatType) {
        this.habitatType = habitatType;
    }

    public String getForestType() {
        return forestType;
    }

    public void setForestType(String forestType) {
        this.forestType = forestType;
    }

    public String getForestAge() {
        return forestAge;
    }

    public void setForestAge(String forestAge) {
        this.forestAge = forestAge;
    }

    public String getTreeType() {
        return treeType;
    }

    public void setTreeType(String treeType) {
        this.treeType = treeType;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public List<String> getPhotos() {
        return photos;
    }

    private void setPhotos(List<Photo> photos) {
        ArrayList<String> photoNames = new ArrayList<>();
        for (Photo photo : photos) {
            photoNames.add(photo.getFileName());
        }
        this.photos = photoNames;
    }

    public String getSample2() {
        return sample2;
    }

    public void setSample2(String sample2) {
        this.sample2 = sample2;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "TransectReportRow_V2{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", numberOfIndividuals=" + numberOfIndividuals +
                ", findingId=" + findingId +
                ", findingType='" + findingType + '\'' +
                ", sample='" + sample + '\'' +
                ", symbol='" + symbol + '\'' +
                ", squareNo=" + squareNo +
                ", created=" + created +
                ", elevation=" + elevation +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", confidence='" + confidence + '\'' +
                ", snow='" + snow + '\'' +
                ", snowType='" + snowType + '\'' +
                ", snowCover='" + snowCover + '\'' +
                ", habitatType='" + habitatType + '\'' +
                ", forestType='" + forestType + '\'' +
                ", forestAge='" + forestAge + '\'' +
                ", treeType='" + treeType + '\'' +
                ", track='" + track + '\'' +
                ", photos=" + photos +
                ", sample2='" + sample2 + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
