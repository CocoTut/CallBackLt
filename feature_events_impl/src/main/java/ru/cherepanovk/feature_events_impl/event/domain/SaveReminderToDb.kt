package ru.cherepanovk.feature_events_impl.event.domain

import ru.cherepanovk.core.exception.ErrorHandler
import ru.cherepanovk.core.interactor.UseCase
import ru.cherepanovk.core_db_api.data.Reminder
import ru.cherepanovk.core_domain_api.data.AlarmApi
import ru.cherepanovk.feature_events_impl.event.data.EventRepository
import javax.inject.Inject

class SaveReminderToDb @Inject constructor(
    private val eventRepository: EventRepository,
    private val alarmApi: AlarmApi,
    errorHandler: ErrorHandler
) : UseCase<Reminder, Reminder>(errorHandler) {

    override suspend fun run(params: Reminder): Reminder {
        cancelCurrentReminder(params.id())

        return eventRepository.saveReminderToDb(params)
    }

    private suspend fun cancelCurrentReminder(reminderId: String) {
        val currentReminder = eventRepository.getReminderFromDb(reminderId)
        val alarmReminder =
            AlarmModel(
                id = currentReminder.id(),
                phoneNumber = currentReminder.phoneNumber(),
                description = currentReminder.description(),
                contactName = currentReminder.contactName(),
                dateTimeEvent = currentReminder.dateTimeEvent()
            )
        alarmApi.cancelAlarm(alarmReminder)
    }

}