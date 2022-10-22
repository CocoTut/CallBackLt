package ru.cherepanovk.feature_events_impl.event.domain

import ru.cherepanovk.core.exception.ErrorHandler
import ru.cherepanovk.core.interactor.UseCase
import ru.cherepanovk.core_preferences_api.data.PreferencesApi
import ru.cherepanovk.feature_alarm_api.data.AlarmApi
import ru.cherepanovk.feature_events_impl.event.data.EventRepository
import ru.cherepanovk.feature_google_calendar_api.data.GoogleCalendarApi
import timber.log.Timber
import javax.inject.Inject

class DeleteReminderFromDb @Inject constructor(
    private val eventRepository: EventRepository,
    private val alarmApi: AlarmApi,
    private val alarmModelMapper: AlarmModelMapper,
    private val googleCalendarApi: GoogleCalendarApi,
    private val preferencesApi: PreferencesApi,
    errorHandler: ErrorHandler
) : UseCase<Unit, String>(errorHandler) {

    override suspend fun run(params: String) {
        cancelAlarm(params)
        eventRepository.deleteReminderById(params)
//        googleCalendarApi.deleteEvent(preferencesApi.getGoogleAccount(), params)
    }

    private suspend fun cancelAlarm(reminderId: String) {
        eventRepository.getReminderFromDb(reminderId)?.let { currentReminder ->
            alarmApi.cancelAlarm(alarmModelMapper.map(currentReminder))
        }

    }
}