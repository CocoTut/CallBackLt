package ru.cherepanovk.feature_google_calendar_impl.data.events

import com.google.api.client.util.DateTime
import com.google.api.services.calendar.model.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.cherepanovk.core.utils.DateTimeHelper
import ru.cherepanovk.core_db_api.data.DbApi
import ru.cherepanovk.core_db_api.data.Reminder
import ru.cherepanovk.feature_alarm_api.data.AlarmApi
import ru.cherepanovk.feature_alarm_api.data.AlarmReminder
import ru.cherepanovk.feature_alarm_api.data.AlarmReminderModel
import ru.cherepanovk.feature_alarm_api.di.CoreDomainApi
import ru.cherepanovk.feature_google_calendar_impl.data.GoogleAccountManager
import java.util.*
import javax.inject.Inject

class GoogleCalendarEventsManager @Inject constructor(
    private val googleAccountManager: GoogleAccountManager,
    private val dbApi: DbApi,
    private val alarmApi: AlarmApi
) {
    suspend fun loadEvents(account: String, startDate: DateTime, endDate: DateTime) {
        return withContext(Dispatchers.IO) {
            val events = googleAccountManager.getGoogleCalendar(account)
                .events()
                .list(account)
                .setTimeMin(startDate)
                .setTimeMax(endDate).execute().items
            saveEventsToDb(events)
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


    companion object {
        private const val ATTENDEE_NAME = "attendee@email.com"
    }
}