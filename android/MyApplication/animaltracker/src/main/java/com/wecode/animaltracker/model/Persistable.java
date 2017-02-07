package com.wecode.animaltracker.model;

import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by sziak on 11/1/2015.
 */
public class Persistable extends SugarRecord implements Comparable<Persistable> {

    private Date created;

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
