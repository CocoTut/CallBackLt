package ru.cherepanovk.feature_events_impl.event.domain

import ru.cherepanovk.core.exception.ErrorHandler
import ru.cherepanovk.core.interactor.UseCase
import ru.cherepanovk.feature_alarm_api.data.AlarmApi
import ru.cherepanovk.feature_events_impl.event.data.EventRepository
import timber.log.Timber
import javax.inject.Inject

class DeleteReminderFromDb @Inject constructor(
    private val eventRepository: EventRepository,
    private val alarmApi: AlarmApi,
    private val alarmModelMapper: AlarmModelMapper,
    errorHandler: ErrorHandler
) : UseCase<Unit, String>(errorHandler) {

    override suspend fun run(params: String) {
        cancelAlarm(params)
        eventRepository.deleteReminderById(params)
    }

    private suspend fun cancelAlarm(reminderId: String) {
        eventRepository.getReminderFromDb(reminderId)?.let { currentReminder ->
            alarmApi.cancelAlarm(alarmModelMapper.map(currentReminder))
        }

    }
}