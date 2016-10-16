package com.wecode.animaltracker.model;

import com.orm.dsl.NotNull;

/**
 * Created by sziak on 16-May-16.
 */
public class Photo extends Persistable {

    public enum EntityName {TRANSECT, TRANECT_FINDING_SITE, TRANECT_FINDING_FOOTPRINT, TRANECT_FINDING_FECES, TRANECT_FINDING_OTHER}

    @NotNull
    private EntityName entityName;

    @NotNull
    private Long entityId;

    @NotNull
    private String fileName;

    private String thumbnail;

    public Photo() {
    }

    public Photo(EntityName entityName, Long entityId, String fileName) {
        this.entityName = entityName;
        this.entityId = entityId;
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

    public EntityName getEntityName() {
        return entityName;
    }

    public void setEntityName(EntityName entityName) {
        this.entityName = entityName;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "entityName='" + entityName + '\'' +
                ", entityId=" + entityId +
                ", fileName='" + fileName + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                "} " + super.toString();
    }
}
