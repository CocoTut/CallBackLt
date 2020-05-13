package ru.cherepanovk.feature_alarm_api.data

import java.util.*

class AlarmReminderModel(
    val id: String,
    val description: String,
    val phoneNumber: String,
    val contactName: String,
    val dateTimeEvent: Date

) : AlarmReminder {
    override fun id() = id

    override fun description() = description

    override fun phoneNumber() = phoneNumber

    override fun contactName() = contactName

    override fun dateTimeEvent() = dateTimeEvent
}
