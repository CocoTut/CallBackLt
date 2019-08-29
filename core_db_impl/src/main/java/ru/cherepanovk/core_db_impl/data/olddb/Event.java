package ru.cherepanovk.core_db_impl.data.olddb;

import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

import ru.cherepanovk.core_db_api.data.Reminder;


public class Event implements Reminder {

    @DatabaseField(id = true)
    public String id;

    @DatabaseField()
    public String description;

    @DatabaseField()
    public String phonenumber;

    @DatabaseField()
    public String contactName;

    @DatabaseField()
    public Date dateTimeEvent;

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

    @Override
    public String id() {
        return id;
    }

    @Override
    public String description() {
        return description;
    }
    @Override
    public String phoneNumber() {
        return phonenumber;
    }
    @Override
    public String contactName() {
        return contactName;
    }
    @Override
    public Date dateTimeEvent() {
        return dateTimeEvent;
    }
}
