package ru.cherepanovk.core_db_impl.db.olddb;

import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

@Deprecated
public class Event {

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



    public void setPhoneNumber(String phoneNumber){
        this.phonenumber = phoneNumber;
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


}
