package ru.cherepanovk.feature_events_impl.events.data

import kotlinx.coroutines.flow.Flow
import ru.cherepanovk.core_db_api.data.models.Reminder
import java.util.*

interface EventsRepository {
    fun getAllEventsFromOldBase(): List<Reminder>

    suspend fun getRemindersFromDb(): List<Reminder>

    suspend fun saveRemindersToDb(reminders: List<Reminder>)

    fun getRemindersBetweenDates(startDate: Date, endDate: Date): Flow<List<Reminder>>

    suspend fun getYears(): List<String>
}