package ru.cherepanovk.core_db_impl.data.olddb;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by CocoNut on 24.10.2017.
 */

public class Account {
    @DatabaseField()
    public String account;
    @DatabaseField(id = true)
    public String id;

   public Account(){}

    public  void setAccount(String account){
        this.account = account;
    }

    public void setId (String id){
        this.id = id;
    }
}
