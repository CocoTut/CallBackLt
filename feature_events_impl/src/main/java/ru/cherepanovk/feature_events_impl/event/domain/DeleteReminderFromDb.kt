package ru.cherepanovk.feature_events_impl.event.domain

import ru.cherepanovk.core.exception.ErrorHandler
import ru.cherepanovk.core.interactor.UseCase
import ru.cherepanovk.core_db_api.data.Reminder
import ru.cherepanovk.core_domain_api.data.AlarmApi
import ru.cherepanovk.feature_events_impl.event.data.EventRepository
import java.util.*
import javax.inject.Inject

class DeleteReminderFromDb @Inject constructor(
    private val eventRepository: EventRepository,
    private val alarmApi: AlarmApi,
    errorHandler: ErrorHandler
) : UseCase<Unit, String>(errorHandler) {

    override suspend fun run(params: String) {
        val reminder = eventRepository.getReminderFromDb(params)

        eventRepository.deleteReminderById(params)

        val alarmModel = AlarmModel(
            id = reminder.id(),
            phoneNumber = reminder.phoneNumber(),
            description = reminder.description(),
            contactName = reminder.contactName(),
            dateTimeEvent = reminder.dateTimeEvent()
        )
        alarmApi.cancelAlarm(alarmModel)
    }
}