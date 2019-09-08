package ru.cherepanovk.feature_events_impl.event

import ru.cherepanovk.core_db_api.data.Reminder
import java.util.*

class NewReminder(
    private val id: String = UUID.randomUUID().toString(),
    private val description: String = "description",
    private val phoneNumber: String = "phoneNumber",
    private val dateTimeEvent: Date = Date(),
    private val contactName: String = "contactName"

) : Reminder {
    override fun id() = id

    override fun description() = description

    override fun phoneNumber() = phoneNumber

    override fun contactName() = contactName

    override fun dateTimeEvent() = dateTimeEvent
}