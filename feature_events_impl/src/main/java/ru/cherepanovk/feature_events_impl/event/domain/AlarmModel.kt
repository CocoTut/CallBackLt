package ru.cherepanovk.feature_events_impl.event.domain

import ru.cherepanovk.feature_alarm_api.data.AlarmReminder
import java.util.*

class AlarmModel (
    private val id: String,
    private val phoneNumber: String = "",
    private val description: String = "",
    private val contactName: String,
    private val dateTimeEvent: Date
): AlarmReminder {
    override fun id() = id
    override fun description() = description
    override fun phoneNumber() = phoneNumber
    override fun contactName() = contactName
    override fun dateTimeEvent() = dateTimeEvent
}