package ru.cherepanovk.core_db_impl.data.olddb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by CocoNut on 29.10.2017.
 */
@Singleton
public class DbHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "dbEvents.db";

    private static final int DATABASE_VERSION = 4;

    private RuntimeExceptionDao<Event, String> eventDao = null;
    private RuntimeExceptionDao<Account, String> accountDao = null;
    private RuntimeExceptionDao<Reschedule, String> rescheduleDao = null;
    private RuntimeExceptionDao<LastPhoneNumber, String> lastPhoneNumberDao = null;
    private static DbHelper helper = null;

    private static final AtomicInteger usageCounter = new AtomicInteger(0);

    @Inject
    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DbHelper getHelper(Context context) {
        if (helper == null) {
            helper = new DbHelper(context);
        }
        usageCounter.incrementAndGet();
        return helper;
    }
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource){
        try {
            Log.i(DbHelper.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, Event.class);
            TableUtils.createTable(connectionSource, Account.class);
            TableUtils.createTable(connectionSource, Reschedule.class);
            TableUtils.createTable(connectionSource, LastPhoneNumber.class);

        } catch (SQLException e) {
            Log.e(DbHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(DbHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, Event.class, true);
            TableUtils.dropTable(connectionSource, Account.class, true);
            TableUtils.dropTable(connectionSource, Reschedule.class, true);
            TableUtils.dropTable(connectionSource, LastPhoneNumber.class, true);
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(DbHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }

    public RuntimeExceptionDao<Event, String> getEventDao() throws SQLException {
        if (eventDao == null) {
            eventDao = getRuntimeExceptionDao(Event.class);
        }
        return eventDao;
    }

    public RuntimeExceptionDao<Account, String> getAccountDao() throws SQLException {
        if (accountDao == null) {
            accountDao = getRuntimeExceptionDao(Account.class);
        }
        return accountDao;
    }

    public RuntimeExceptionDao<Reschedule, String> getRescheduleDao() throws SQLException {
        if (rescheduleDao == null) {
            rescheduleDao = getRuntimeExceptionDao(Reschedule.class);
        }
        return rescheduleDao;
    }

    public RuntimeExceptionDao<LastPhoneNumber, String> getLastPhoneNumberDao() throws SQLException {
        if (lastPhoneNumberDao == null) {
            lastPhoneNumberDao = getRuntimeExceptionDao(LastPhoneNumber.class);
        }
        return lastPhoneNumberDao;
    }


    @Override
    public void close() {
        super.close();

        if(accountDao != null){
            accountDao = null;
        }

        if(eventDao != null){
            eventDao = null;
        }

        if(rescheduleDao != null){
            rescheduleDao = null;
        }

        if(lastPhoneNumberDao != null){
            lastPhoneNumberDao = null;
        }

    }

}
