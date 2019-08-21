package com.cherepanovky.callbackit.core.storage.olddb;

import com.j256.ormlite.field.DatabaseField;

import java.util.Date;


public class Event {

    @DatabaseField(id = true)
    public String id;

    @DatabaseField()
    public String description;

//    @DatabaseField()
//    public String datetimeStart;
//
//    @DatabaseField()
//    public String datetimeEnd;

    @DatabaseField()
    public String phonenumber;

    @DatabaseField()
    public String contactName;

    @DatabaseField()
    public Date dateTimeEvent;

//    @DatabaseField()
//    public String accountName;

    public Event(){}



    public void setPhonenumber (String phonenumber){
        this.phonenumber = phonenumber;
    }
    public void setContactName (String contactName){
        this.contactName = contactName;
    }
    public void setDateTimeEvent (Date dateTimeEvent){
        this.dateTimeEvent = dateTimeEvent;
    }
    public void setDescription (String description){
        this.description = description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public String getContactName() {
        return contactName;
    }

    public Date getDateTimeEvent() {
        return dateTimeEvent;
    }
}
