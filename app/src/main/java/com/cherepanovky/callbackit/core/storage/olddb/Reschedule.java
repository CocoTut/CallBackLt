package com.cherepanovky.callbackit.core.storage.olddb;

/**
 * Created by CocoNut on 23.10.2017.
 */

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by CocoNut on 26.09.2017.
 */

public class Reschedule {

    @DatabaseField()
    public
    int value;

    @DatabaseField(generatedId = true)
    public int id;

    @DatabaseField()
    public String unit;

    @DatabaseField()
    public String description;

    public Reschedule(){}

    public void setValue (int value){
        this.value = value;
    }

    public void setUnit (String unit){
        this.unit = unit;
    }

    public int getValue() {
        return value;
    }

    public int getId() {
        return id;
    }

    public String getUnit() {
        return unit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

