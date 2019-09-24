package ru.cherepanovk.feature_events_impl.event

import ru.cherepanovk.core_db_api.data.Reminder
import java.util.*

class NewReminder(
    private val id: String,
    private val description: String,
    private val phoneNumber: String,
    private val dateTimeEvent: Date,
    private val contactName: String

) : Reminder {
    override fun id() = id

    override fun description() = description

    override fun phoneNumber() = phoneNumber

    override fun contactName() = contactName

    override fun dateTimeEvent() = dateTimeEvent
}