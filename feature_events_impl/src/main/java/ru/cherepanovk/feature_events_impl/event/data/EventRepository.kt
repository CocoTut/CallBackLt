package ru.cherepanovk.feature_events_impl.event.data

import ru.cherepanovk.core_db_api.data.models.Reminder

interface EventRepository {
    suspend fun getReminderFromDb(id: String): Reminder?

    suspend fun saveReminderToDb(reminder: Reminder): Reminder

    suspend fun deleteReminderById(id: String)

    suspend fun updateReminder(reminder: Reminder)
}