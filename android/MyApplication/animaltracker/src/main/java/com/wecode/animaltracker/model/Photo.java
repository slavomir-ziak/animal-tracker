package com.wecode.animaltracker.model;

/**
 * Created by sziak on 16-May-16.
 */
public class Photo extends Persistable {

    private Long transectFindingId;
    private String fileName;
    private String thumbnail;

    public Photo() {
    }
    public Photo(Long id, Long transectFindingId, String fileName, String thumbnail) {
        setId(id);
        this.transectFindingId = transectFindingId;
        this.thumbnail = thumbnail;
        this.fileName = fileName;
    }

    public Photo(Long transectFindingId, String fileName) {
        this.transectFindingId = transectFindingId;
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Long getTransectFindingId() {
        return transectFindingId;
    }

    public void setTransectFindingId(Long transectFindingId) {
        this.transectFindingId = transectFindingId;
    }
}
