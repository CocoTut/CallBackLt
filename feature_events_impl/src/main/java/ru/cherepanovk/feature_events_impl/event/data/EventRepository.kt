package ru.cherepanovk.feature_events_impl.event.data

import ru.cherepanovk.core_db_api.data.Reminder

interface EventRepository {
    suspend fun getReminderFromDb(id: String): Reminder
}