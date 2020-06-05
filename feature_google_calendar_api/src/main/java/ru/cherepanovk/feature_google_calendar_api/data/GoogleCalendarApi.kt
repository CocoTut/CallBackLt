package ru.cherepanovk.feature_google_calendar_api.data

import kotlinx.coroutines.flow.Flow
import java.util.*

interface GoogleCalendarApi {
    fun savingAccount(): Flow<Boolean>

    suspend fun loadEvents(account: String, startDate: Date, endDate: Date)

    suspend fun saveEvent(account: String, event: GoogleCalendarEvent)

    suspend fun deleteEvent(account: String, eventId: String)

    suspend fun updateEvent(account: String, event: GoogleCalendarEvent)
}