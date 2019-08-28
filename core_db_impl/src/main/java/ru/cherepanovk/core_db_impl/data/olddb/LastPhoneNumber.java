package ru.cherepanovk.core_db_impl.data.olddb;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by CocoNut on 07.11.2017.
 */

public class LastPhoneNumber {
    @DatabaseField()
    public
    String phoneNumber;
    @DatabaseField(id = true)
    public
    String id;

    public LastPhoneNumber(){}

    public  void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public void setId (String id){
        this.id = id;
    }
}
