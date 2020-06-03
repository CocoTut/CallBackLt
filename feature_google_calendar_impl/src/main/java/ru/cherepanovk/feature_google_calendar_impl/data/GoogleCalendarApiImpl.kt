package ru.cherepanovk.feature_google_calendar_impl.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.cherepanovk.feature_google_calendar_api.data.GoogleCalendarApi
import ru.cherepanovk.feature_google_calendar_api.data.GoogleCalendarEvent
import ru.cherepanovk.feature_google_calendar_impl.data.events.GoogleCalendarEventsManager
import java.util.*
import javax.inject.Inject
@ExperimentalCoroutinesApi
class GoogleCalendarApiImpl @Inject constructor(
   private val accountPermissionManager: AccountPermissionManager,
   private val googleCalendarEventsManager: GoogleCalendarEventsManager
) : GoogleCalendarApi {

    override fun savingAccount() = accountPermissionManager.savingAccount()

    override suspend fun loadEvents(account: String, startDate: Date, endDate: Date) {
        googleCalendarEventsManager.loadEvents(account, startDate, endDate)
    }

    override suspend fun saveEvent(account: String, event: GoogleCalendarEvent) {
        googleCalendarEventsManager.saveEvent(account, event)
    }

    override suspend fun deleteEvent(account: String, eventId: String) {
        googleCalendarEventsManager.deleteEvent(account, eventId)
    }

}