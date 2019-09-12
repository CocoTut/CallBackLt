package ru.cherepanovk.feature_events_impl.events.data

import ru.cherepanovk.core_db_api.data.DbApi
import ru.cherepanovk.core_db_api.data.Reminder
import java.util.*
import javax.inject.Inject

class EventsRepositoryImpl @Inject constructor(
    private val dataBase: DbApi
) : EventsRepository {
    override suspend fun getRemindersFromDb(): List<Reminder> {
        return dataBase.getAllReminders()
    }

    override fun getAllEventsFromOldBase(): List<Reminder> {
        return dataBase.getRemindersFromOldDb()
    }

    override suspend fun saveRemindersToDb(reminders: List<Reminder>) {
        dataBase.saveReminders(reminders)
    }

    override suspend fun getRemindersBetweenDates(startDate: Date, endDate: Date): List<Reminder> {
        return dataBase.getRemindersBetweenDates(startDate, endDate)
    }
}