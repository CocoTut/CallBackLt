package ru.cherepanovk.feature_events_impl.event.domain

import ru.cherepanovk.core.exception.ErrorHandler
import ru.cherepanovk.core.interactor.UseCase
import ru.cherepanovk.core_db_api.data.Reminder
import ru.cherepanovk.core_domain_api.data.AlarmApi
import ru.cherepanovk.core_domain_api.data.AlarmReminder
import ru.cherepanovk.feature_events_impl.event.ReminderView
import ru.cherepanovk.feature_events_impl.event.data.EventRepository
import java.util.*
import javax.inject.Inject

class CreateReminderAlarm @Inject constructor(
    private val alarmApi: AlarmApi,
    errorHandler: ErrorHandler
) : UseCase<Unit, Reminder>(errorHandler) {

    override suspend fun run(params: Reminder) {
       val alarmReminder =
           AlarmModel(
               id = params.id(),
               phoneNumber = params.phoneNumber(),
               description = params.description(),
               contactName = params.contactName(),
               dateTimeEvent = params.dateTimeEvent()
           )
       return alarmApi.createAlarm(alarmReminder)
    }


}