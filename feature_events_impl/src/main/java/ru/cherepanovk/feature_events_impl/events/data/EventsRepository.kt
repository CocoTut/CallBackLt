package ru.cherepanovk.feature_events_impl.events.data

import ru.cherepanovk.core_db_api.data.Reminder
import java.util.*

interface EventsRepository {
    fun getAllEventsFromOldBase(): List<Reminder>

    suspend fun getRemindersFromDb(): List<Reminder>

    suspend fun saveRemindersToDb(reminders: List<Reminder>)

    suspend fun getRemindersBetweenDates(startDate: Date, endDate: Date): List<Reminder>

    suspend fun getYears(): List<String>
}