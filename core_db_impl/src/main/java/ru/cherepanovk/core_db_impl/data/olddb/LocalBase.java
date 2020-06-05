package ru.cherepanovk.core_db_impl.data.olddb;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.cherepanovk.core_db_api.data.Reminder;

/**
 * Created by CocoNut on 18.05.2018.
 */
@Deprecated
@Singleton
public class LocalBase {

    private static final String ACCOUNT_ID = "accountID";
    private static final String ID_LASTPHONENUMBER = "phoneNumber";

    private DbHelper dbHelper;
    private RuntimeExceptionDao<Event, String> eventDAO = null;
    private RuntimeExceptionDao<Account, String> accountDAO = null;
    private RuntimeExceptionDao<Reschedule, String> rescheduleDAO = null;
    private RuntimeExceptionDao<LastPhoneNumber, String> lastPhoneNumberDAO = null;

    @Inject
    public LocalBase(DbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    private void setEventDAO() {
        try {
            eventDAO = dbHelper.getEventDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setAccountDAO() {
        try {
            accountDAO = dbHelper.getAccountDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setRescheduleDAO() {
        try {
            rescheduleDAO = dbHelper.getRescheduleDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setLastPhoneNumberDAO() {
        try {
            lastPhoneNumberDAO = dbHelper.getLastPhoneNumberDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @NotNull
    public List<Reminder> getAllEvents() {
        setEventDAO();
        ArrayList<Reminder> reminders = new ArrayList<>();
        List<Event> events = eventDAO.queryForAll();
        if (events != null)
            for (Event event : events) {
                Reminder reminder = new Reminder(
                        event.id,
                        event.description,
                        event.phonenumber,
                        event.contactName,
                        event.dateTimeEvent
                );
                reminders.add(reminder);
            }

        return reminders;
    }

    public String saveLocalEvent(String phoneNumber, String contactName, String description,
                                 Date date, String id) {
        setEventDAO();
        Event event = new Event();
        event.setPhoneNumber(phoneNumber);
        event.setContactName(contactName);
        event.setDescription(description);
        event.setDateTimeEvent(date);
        event.setId(UUID.randomUUID().toString());
        if (id != null) {
            event.setId(id);
        }
        eventDAO.create(event);

        return event.id;
    }

    public void editLocalEvent(String phoneNumber, String contactName, String description, Date date,
                               String eventID) {
        setEventDAO();
        Event event = getEventByID(eventID);
        event.setPhoneNumber(phoneNumber);
        event.setContactName(contactName);
        event.setDescription(description);
        event.setDateTimeEvent(date);
        eventDAO.update(event);

    }

    public void updateEvent(Event event) {
        setEventDAO();
        eventDAO.update(event);
    }

    public List<Event> getEventsForMonth(Date startEventsDate, Date endEventsDate) {

        setEventDAO();

        List<Event> events = null;

        QueryBuilder<Event, String> queryBuilder = eventDAO.queryBuilder();
        PreparedQuery<Event> preparedQuery = null;
        try {
            queryBuilder.where().between("dateTimeEvent", startEventsDate, endEventsDate);
            preparedQuery = queryBuilder.prepare();
            events = eventDAO.query(preparedQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        if (events.size() != 0) {
            Collections.sort(events, new Comparator<Event>() {
                public int compare(Event obj1, Event obj2) {
                    return obj1.dateTimeEvent.compareTo(obj2.dateTimeEvent);
                }
            });

        }
        return events;

    }

    public List<Event> getCurrentEvents(Date startEventsDate) {

        setEventDAO();

        List<Event> events = null;

        QueryBuilder<Event, String> queryBuilder = eventDAO.queryBuilder();
        PreparedQuery<Event> preparedQuery = null;
        try {
            queryBuilder.where().gt("dateTimeEvent", startEventsDate);
            preparedQuery = queryBuilder.prepare();
            events = eventDAO.query(preparedQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        if (events.size() != 0) {
            Collections.sort(events, new Comparator<Event>() {
                public int compare(Event obj1, Event obj2) {
                    return obj1.dateTimeEvent.compareTo(obj2.dateTimeEvent);
                }
            });

        }
        return events;

    }

    public Event getEventByID(String eventID) {
        setEventDAO();
        Event event = eventDAO.queryForId(eventID);
        return event;
    }

    public String getEventIDByPhoneNumber(String phoneNumber) {

        setEventDAO();

        String searchingPhoneNumber = phoneNumber.replaceAll("\\D+", "");
        if (searchingPhoneNumber.isEmpty()) {
            return null;
        }
        searchingPhoneNumber = searchingPhoneNumber.substring(1, searchingPhoneNumber.length() - 1);

        QueryBuilder<Event, String> queryBuilder = eventDAO.queryBuilder();
        PreparedQuery<Event> preparedQuery = null;
        List<Event> events = null;
        String eventID = null;
        try {
            queryBuilder.where().like("phonenumber", "%" + searchingPhoneNumber + "%");
            preparedQuery = queryBuilder.prepare();
            events = eventDAO.query(preparedQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (events.size() > 0) {
            Collections.sort(events, new Comparator<Event>() {
                public int compare(Event obj1, Event obj2) {
                    return obj1.dateTimeEvent.compareTo(obj2.dateTimeEvent);
                }
            });

            eventID = events.get(events.size() - 1).id;
        }
        return eventID;

    }

    public void deleteEventById(String eventID) {
        setEventDAO();
        eventDAO.deleteById(eventID);

    }

    public void setAccount(String accountName) {
        if (getAccount() != null) {
            Account account = getAccount();
            account.setAccount(accountName);
            accountDAO.update(account);
            return;
        }
        Account account = new Account();
        account.setAccount(accountName);
        account.setId(ACCOUNT_ID);
        accountDAO.create(account);

    }

    public Account getAccount() {
        setAccountDAO();
        return accountDAO.queryForId(ACCOUNT_ID);
    }

    public void deleteAccount() {
        setAccountDAO();
        accountDAO.deleteById(ACCOUNT_ID);
    }

    public List<Reschedule> getListReschedule() {
        setRescheduleDAO();
        return rescheduleDAO.queryForAll();
    }

    public void createReschedule(Reschedule reschedule) {
        setRescheduleDAO();
        rescheduleDAO.create(reschedule);
    }

    public void deleteReschedule(int id) {
        setRescheduleDAO();
        rescheduleDAO.delete(rescheduleDAO.queryForId(id + ""));


    }

    public void createLastPhoneNumber(String phoneNumber) {
        setLastPhoneNumberDAO();
        LastPhoneNumber lastPhoneNumber = lastPhoneNumberDAO.queryForId(ID_LASTPHONENUMBER);
        if (lastPhoneNumber == null) {
            lastPhoneNumber = new LastPhoneNumber();
            lastPhoneNumber.setId(ID_LASTPHONENUMBER);
            lastPhoneNumber.setPhoneNumber(phoneNumber);
            lastPhoneNumberDAO.create(lastPhoneNumber);
            return;
        }
        lastPhoneNumber.setPhoneNumber(phoneNumber);
        lastPhoneNumberDAO.update(lastPhoneNumber);
    }

    public String getLastPhoneNumber() {
        setLastPhoneNumberDAO();
        LastPhoneNumber lastPhoneNumber = lastPhoneNumberDAO.queryForId(ID_LASTPHONENUMBER);
        if (lastPhoneNumber != null) {
            return lastPhoneNumber.phoneNumber;
        }
        return null;
    }

    public void deleteLastPhoneNumber() {
        setLastPhoneNumberDAO();
        lastPhoneNumberDAO.deleteById(ID_LASTPHONENUMBER);
    }

}
