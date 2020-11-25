package ru.cherepanovk.feature_google_calendar_impl.data.events

import com.google.api.client.util.DateTime
import com.google.api.services.calendar.model.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.cherepanovk.core.exception.CallBackItException
import ru.cherepanovk.core_db_api.data.RemindersDbApi
import ru.cherepanovk.core_db_api.data.models.Reminder
import ru.cherepanovk.feature_alarm_api.data.AlarmApi
import ru.cherepanovk.feature_alarm_api.data.AlarmReminderModel
import ru.cherepanovk.feature_google_calendar_api.data.GoogleCalendarEvent
import ru.cherepanovk.feature_google_calendar_impl.data.GoogleAccountManager
import java.util.*
import javax.inject.Inject

class GoogleCalendarEventsManager @Inject constructor(
    private val googleAccountManager: GoogleAccountManager,
    private val remindersDbApi: RemindersDbApi,
    private val alarmApi: AlarmApi,
    private val calendarEventMapper: CalendarEventMapper
) {

    suspend fun loadEvents(account: String, startDate: Date, endDate: Date) {
        if (account.isEmpty()) throw CallBackItException.HasNoAccount
        return withContext(Dispatchers.IO) {
            val events = googleAccountManager.getGoogleCalendar(account)
                .events()
                .list(account)
                .setTimeMin(DateTime(startDate))
                .setTimeMax(DateTime(endDate)).execute().items
            saveEventsToDb(events)
        }

    }

    suspend fun saveEvent(account: String, calendarEvent: GoogleCalendarEvent) {
        withContext(Dispatchers.IO) {
            googleAccountManager.getGoogleCalendar(account)
                .events()
                .insert(account, calendarEventMapper.map(calendarEvent))
                .execute()

        }
    }


    private suspend fun saveEventsToDb(events: List<Event>) {
        val filtered = events.filter { event ->
            event.attendees?.any { it?.email == ATTENDEE_NAME } ?: false
                    || (event.extendedProperties != null && event.extendedProperties.isNotEmpty())
        }
        val reminders =
            filtered.map { event ->
                val properties = event.extendedProperties?.let { CalendarEventExtendedProperties.fromExtendedProperties(event.extendedProperties) }
                Reminder(
                    id = event.id,
                    description = event.description ?: "",
                    contactName = properties?.contactName ?: event.attendees.first()?.displayName ?: "",
                    phoneNumber = properties?.phoneNumber ?: event.attendees.first()?.comment ?: "",
                    dateTimeEvent = Date(event.start.dateTime.value)
                )

            }


        remindersDbApi.saveReminders(reminders)
        reminders.forEach {
            alarmApi.createAlarm(
                AlarmReminderModel(
                    id = it.id,
                    phoneNumber = it.phoneNumber,
                    contactName = it.contactName,
                    dateTimeEvent = it.dateTimeEvent,
                    description = it.description
                )
            )
        }
    }

    suspend fun deleteEvent(account: String, eventId: String) {
        if (account.isEmpty()) return

        withContext(Dispatchers.IO) {
            googleAccountManager.getGoogleCalendar(account)
                .events().delete(account, eventId).execute()
        }

    }

    suspend fun updateEvent(account: String, calendarEvent: GoogleCalendarEvent) {
        if (account.isEmpty()) return

        withContext(Dispatchers.IO) {
            val event = calendarEventMapper.map(calendarEvent)
            googleAccountManager.getGoogleCalendar(account)
                .events().update(account, event.id, event).execute()
        }

    }

    companion object {
        internal const val ATTENDEE_NAME = "attendee@email.com"
    }

}