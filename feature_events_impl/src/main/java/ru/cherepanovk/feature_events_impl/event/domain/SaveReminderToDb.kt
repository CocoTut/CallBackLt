package ru.cherepanovk.feature_events_impl.event.domain

import ru.cherepanovk.core.exception.ErrorHandler
import ru.cherepanovk.core.interactor.UseCase
import ru.cherepanovk.core.utils.DateTimeHelper
import ru.cherepanovk.core_db_api.data.Reminder
import ru.cherepanovk.core_preferences_api.data.PreferencesApi
import ru.cherepanovk.feature_alarm_api.data.AlarmApi
import ru.cherepanovk.feature_events_impl.event.data.EventRepository
import ru.cherepanovk.feature_google_calendar_api.data.GoogleCalendarApi
import ru.cherepanovk.feature_google_calendar_api.data.GoogleCalendarEvent
import javax.inject.Inject

class SaveReminderToDb @Inject constructor(
    private val eventRepository: EventRepository,
    private val alarmApi: AlarmApi,
    private val alarmModelMapper: AlarmModelMapper,
    private val googleCalendarApi: GoogleCalendarApi,
    private val preferencesApi: PreferencesApi,
    private val dateTimeHelper: DateTimeHelper,
    errorHandler: ErrorHandler
) : UseCase<Unit, Reminder>(errorHandler) {

    override suspend fun run(params: Reminder) {


        eventRepository.saveReminderToDb(params).run {
            cancelCurrentReminder(this.id)
            createAlarm(this.id)
            saveReminderToGoogleCalendar(this)
        }


    }

    private suspend fun saveReminderToGoogleCalendar(params: Reminder) {
        preferencesApi.getGoogleAccount().run {
            if (this.isEmpty())
                return
            googleCalendarApi.saveEvent(
                this,
                GoogleCalendarEvent(
                    id = params.id,
                    description = params.description,
                    contactName = params.contactName,
                    phoneNumber = params.phoneNumber,
                    startTime = params.dateTimeEvent,
                    endTime = dateTimeHelper.addTimeToDate(params.dateTimeEvent, 1)
                )
            )
        }
    }

    private suspend fun cancelCurrentReminder(reminderId: String) {
        eventRepository.getReminderFromDb(reminderId)?.let { currentReminder ->
            alarmApi.cancelAlarm(alarmModelMapper.map(currentReminder))
        }

    }

    private suspend fun createAlarm(reminderId: String) {
        eventRepository.getReminderFromDb(reminderId)?.let { reminder ->
            alarmApi.createAlarm(alarmModelMapper.map(reminder))
        }
    }

}