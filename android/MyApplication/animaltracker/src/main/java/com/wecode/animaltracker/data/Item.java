package com.wecode.animaltracker.data;

import android.location.Location;

import java.util.Date;

/**
 * Created by sziak on 10/2/2015.
 */
public class Item {

    private Location location;

    private Date created;

    private Date modified;

    private String fileName;

    private String note;

    public Item(Location location, Date created, Date modified, String fileName, String note) {
        this.location = location;
        this.created = created;
        this.modified = modified;
        this.fileName = fileName;
        this.note = note;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
