package ru.cherepanovk.feature_events_impl.event.data

import ru.cherepanovk.core_db_api.data.RemindersDbApi
import ru.cherepanovk.core_db_api.data.models.Reminder
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(private val dataBase: RemindersDbApi) : EventRepository {

    override suspend fun getReminderFromDb(id: String): Reminder? {
        return dataBase.getReminderById(id)
    }

    override suspend fun saveReminderToDb(reminder: Reminder): Reminder {
       return dataBase.saveReminder(reminder)
    }

    override suspend fun deleteReminderById(id: String) {
        dataBase.deleteReminderById(id)
    }

    override suspend fun updateReminder(reminder: Reminder) {

    }
}