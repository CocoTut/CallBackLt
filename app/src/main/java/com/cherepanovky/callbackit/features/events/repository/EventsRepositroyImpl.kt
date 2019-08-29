package com.cherepanovky.callbackit.features.events.repository

import ru.cherepanovk.core_db_api.data.DbApi
import ru.cherepanovk.core_db_api.data.Reminder
import javax.inject.Inject

class EventsRepositroyImpl @Inject constructor(
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
}