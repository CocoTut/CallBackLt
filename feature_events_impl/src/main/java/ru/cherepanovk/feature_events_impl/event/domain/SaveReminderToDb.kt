package ru.cherepanovk.feature_events_impl.event.domain

import ru.cherepanovk.core.exception.ErrorHandler
import ru.cherepanovk.core.interactor.UseCase
import ru.cherepanovk.core_db_api.data.Reminder
import ru.cherepanovk.feature_alarm_api.data.AlarmApi
import ru.cherepanovk.feature_events_impl.event.data.EventRepository
import javax.inject.Inject

class SaveReminderToDb @Inject constructor(
    private val eventRepository: EventRepository,
    private val alarmApi: AlarmApi,
    private val alarmModelMapper: AlarmModelMapper,
    errorHandler: ErrorHandler
) : UseCase<Unit, Reminder>(errorHandler) {

    override suspend fun run(params: Reminder) {

        cancelCurrentReminder(params.id)
        eventRepository.saveReminderToDb(params)
        createAlarm(params.id)
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