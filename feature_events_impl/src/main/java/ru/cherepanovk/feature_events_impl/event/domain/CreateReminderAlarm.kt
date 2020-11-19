package ru.cherepanovk.feature_events_impl.event.domain

import ru.cherepanovk.core.exception.ErrorHandler
import ru.cherepanovk.core.interactor.UseCase
import ru.cherepanovk.core_db_api.data.models.Reminder
import ru.cherepanovk.feature_alarm_api.data.AlarmApi
import javax.inject.Inject

class CreateReminderAlarm @Inject constructor(
    private val alarmApi: AlarmApi,
    errorHandler: ErrorHandler
) : UseCase<Unit, Reminder>(errorHandler) {

    override suspend fun run(params: Reminder) {
       val alarmReminder =
           AlarmModel(
               id = params.id,
               phoneNumber = params.phoneNumber,
               description = params.description,
               contactName = params.contactName,
               dateTimeEvent = params.dateTimeEvent
           )
       return alarmApi.createAlarm(alarmReminder)
    }


}