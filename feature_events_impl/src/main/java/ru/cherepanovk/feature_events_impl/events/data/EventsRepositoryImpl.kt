package ru.cherepanovk.feature_events_impl.events.data

import kotlinx.coroutines.flow.Flow
import ru.cherepanovk.core_db_api.data.RemindersDbApi
import ru.cherepanovk.core_db_api.data.models.Reminder
import java.util.*
import javax.inject.Inject

class EventsRepositoryImpl @Inject constructor(
    private val dataBase: RemindersDbApi
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

    override fun getRemindersBetweenDates(startDate: Date, endDate: Date): Flow<List<Reminder>> {
        return dataBase.getRemindersBetweenDates(startDate, endDate)
    }

    override fun getYears(): Flow<List<String>>  {
        return  dataBase.getYears()
    }
}