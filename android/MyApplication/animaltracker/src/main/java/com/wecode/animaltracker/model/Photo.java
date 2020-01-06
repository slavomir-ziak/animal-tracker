package com.wecode.animaltracker.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by sziak on 16-May-16.
 */
@DatabaseTable(tableName = "PHOTO")
public class Photo extends Persistable {

    public static final String ENTITY_NAME_COLUMN = "ENTITY_NAME";

    public static final String ENTITY_ID_COLUMN = "ENTITY_ID";

    public static final String FILE_NAME_COLUMN = "FILE_NAME";

    public static final String THUMBNAIL_COLUMN = "THUMBNAIL";

    @DatabaseField(columnName = ENTITY_NAME_COLUMN, canBeNull = false)
    private EntityName entityName;

    @DatabaseField(columnName = ENTITY_ID_COLUMN, canBeNull = false)
    private Long entityId;

    @DatabaseField(columnName = FILE_NAME_COLUMN, canBeNull = false)
    private String fileName;

    @DatabaseField(columnName = THUMBNAIL_COLUMN)
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
