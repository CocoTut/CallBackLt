package ru.cherepanovk.feature_events_impl.event.data

import ru.cherepanovk.core_db_api.data.DbApi
import ru.cherepanovk.core_db_api.data.Reminder
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(private val dataBase: DbApi) : EventRepository {

    override suspend fun getReminderFromDb(id: String): Reminder {
        return dataBase.getReminderById(id)
    }

    override suspend fun saveReminderToDb(reminder: Reminder) {
        dataBase.saveReminderTodb(reminder)
    }
}