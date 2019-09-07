package ru.cherepanovk.feature_events_impl.events.data

import ru.cherepanovk.core_db_api.data.Reminder

interface EventsRepository {
    fun getAllEventsFromOldBase(): List<Reminder>

    suspend fun getRemindersFromDb(): List<Reminder>

    suspend fun saveRemindersToDb(reminders: List<Reminder>)
}