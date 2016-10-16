package com.wecode.animaltracker.model;

import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by sziak on 11/1/2015.
 */
public class Persistable extends SugarRecord implements Comparable<Persistable> {

    private Date created;

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "Persistable{" +
                "created=" + created +
                '}';
    }

    @Override
    public int compareTo(Persistable anotherPersistable) {
        return this.getCreated().compareTo(anotherPersistable.getCreated());
    }
}
