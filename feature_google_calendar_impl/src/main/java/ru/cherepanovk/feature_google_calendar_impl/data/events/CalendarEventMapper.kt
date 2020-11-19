package ru.cherepanovk.feature_google_calendar_impl.data.events

import com.google.api.client.util.DateTime
import com.google.api.services.calendar.model.Event
import com.google.api.services.calendar.model.EventAttendee
import com.google.api.services.calendar.model.EventDateTime
import com.google.api.services.calendar.model.EventReminder
import ru.cherepanovk.feature_google_calendar_api.data.GoogleCalendarEvent
import ru.cherepanovk.feature_google_calendar_impl.data.events.GoogleCalendarEventsManager.Companion.ATTENDEE_NAME
import java.util.*
import javax.inject.Inject



class CalendarEventMapper @Inject constructor() {

    fun map(calendarEvent: GoogleCalendarEvent): Event {
        return Event().apply {
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
}