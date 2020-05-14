package ru.cherepanovk.feature_google_calendar_impl.data.events

import com.google.api.client.util.DateTime
import com.google.api.services.calendar.model.Event
import com.google.api.services.calendar.model.EventAttendee
import com.google.api.services.calendar.model.EventDateTime
import com.google.api.services.calendar.model.EventReminder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.cherepanovk.core.exception.CallBackItException
import ru.cherepanovk.core_db_api.data.DbApi
import ru.cherepanovk.core_db_api.data.Reminder
import ru.cherepanovk.feature_alarm_api.data.AlarmApi
import ru.cherepanovk.feature_alarm_api.data.AlarmReminderModel
import ru.cherepanovk.feature_google_calendar_api.data.GoogleCalendarEvent
import ru.cherepanovk.feature_google_calendar_impl.data.GoogleAccountManager
import java.util.*
import javax.inject.Inject

class GoogleCalendarEventsManager @Inject constructor(
    private val googleAccountManager: GoogleAccountManager,
    private val dbApi: DbApi,
    private val alarmApi: AlarmApi
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
          val event =   Event().apply {
                id = calendarEvent.id
                attendees = listOf(
                    EventAttendee().setEmail(ATTENDEE_NAME).setComment(calendarEvent.phoneNumber)
                        .setDisplayName(calendarEvent.contactName)
                )
                reminders = getCalendarReminders()
                summary =
                    if (calendarEvent.description.isEmpty()) calendarEvent.contactName
                    else calendarEvent.contactName + ", " + calendarEvent.description
                description = calendarEvent.description
                start = EventDateTime().apply {
                    dateTime = DateTime(calendarEvent.startTime)
                    timeZone = TimeZone.getDefault().id
                }
                end = EventDateTime().apply {
                    dateTime = DateTime(calendarEvent.endTime)
                    timeZone = TimeZone.getDefault().id
                }
            }
            googleAccountManager.getGoogleCalendar(account)
                .events()
                .insert(account, event)
                .execute()

        }
    }


    private fun getCalendarReminders(): Event.Reminders {
        return Event.Reminders().apply {
            useDefault = false
            overrides = listOf(EventReminder().apply {
                method = "popup"
                minutes = 1
            })
        }
    }

    private suspend fun saveEventsToDb(events: List<Event>) {
        val filtered = events.filter { event ->
            event.attendees?.any { it?.email == ATTENDEE_NAME } ?: false
        }
        val reminders =
            filtered.map { event ->
                Reminder(
                    id = event.id,
                    description = event.description,
                    contactName = event.attendees.first().displayName,
                    phoneNumber = event.attendees.first().comment,
                    dateTimeEvent = Date(event.start.dateTime.value)
                )

            }


        dbApi.saveReminders(reminders)
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

    suspend fun deleteEvent(account: String, event: GoogleCalendarEvent) {
        withContext(Dispatchers.IO) {
            googleAccountManager.getGoogleCalendar(account)
                .events().delete(account, event.id)
        }
    }


    companion object {
        private const val ATTENDEE_NAME = "attendee@email.com"
    }
}