package com.wecode.animaltracker.model;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by sziak on 11/1/2015.
 */
public abstract class Persistable implements Comparable<Persistable>, Serializable {

    public static final String ID_COLUMN = "ID";

    public static final String CREATED_COLUMN = "CREATED";

    public static final String COMMENT_COLUMN = "COMMENT";

    @DatabaseField(generatedId = true, columnName = ID_COLUMN)
    private Long id;

    @DatabaseField(columnName = CREATED_COLUMN)
    private Date created;

    @DatabaseField(columnName = COMMENT_COLUMN)
    private String comment;

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Persistable{" +
                "id=" + getId() +
                "created=" + created +
                "comment=" + comment +
                '}';
    }

    @Override
    public int compareTo(Persistable anotherPersistable) {
        return this.getCreated().compareTo(anotherPersistable.getCreated());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Persistable)) return false;

        Persistable that = (Persistable) o;

        return getId() != null ? getId().equals(that.getId()) : that.getId() == null;

    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}
