package ru.cherepanovk.feature_events_impl.event.domain

import ru.cherepanovk.core.exception.ErrorHandler
import ru.cherepanovk.core.interactor.UseCase
import ru.cherepanovk.core_db_api.data.Reminder
import ru.cherepanovk.core_domain_api.data.AlarmApi
import ru.cherepanovk.core_domain_api.data.AlarmReminder
import ru.cherepanovk.feature_events_impl.event.ReminderView
import java.util.*
import javax.inject.Inject

class CreateReminderAlarm @Inject constructor(
    private val alarmApi: AlarmApi,
    errorHandler: ErrorHandler
) : UseCase<Unit, ReminderView>(errorHandler) {

    override suspend fun run(params: ReminderView) {
       val alarmReminder =
           AlarmModel(
               id = params.id ?: UUID.randomUUID().toString(),
               phoneNumber = params.phoneNumber,
               description = params.description,
               contactName = params.contactName,
               dateTimeEvent = Date()
           )
       return alarmApi.createAlarm(alarmReminder)
    }


    private class AlarmModel (
        private val id: String,
        private val phoneNumber: String = "",
        private val description: String = "",
        private val contactName: String,
        private val dateTimeEvent: Date
    ): AlarmReminder{
        override fun id() = id
        override fun description() = description
        override fun phoneNumber() = phoneNumber
        override fun contactName() = contactName
        override fun dateTimeEvent() = dateTimeEvent
    }
}