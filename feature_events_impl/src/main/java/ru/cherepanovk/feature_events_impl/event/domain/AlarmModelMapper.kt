package ru.cherepanovk.feature_events_impl.event.domain

import ru.cherepanovk.core_db_api.data.models.Reminder
import java.util.*
import javax.inject.Inject

class AlarmModelMapper @Inject constructor() {
    fun map(reminder: Reminder) =
        AlarmModel(
            id = reminder.id,
            phoneNumber = reminder.phoneNumber,
            description = reminder.description,
            contactName = reminder.contactName,
            dateTimeEvent = reminder.dateTimeEvent
        )

    fun map(reminder: Reminder, dateTime: Date) =
        AlarmModel(
            id = reminder.id,
            phoneNumber = reminder.phoneNumber,
            description = reminder.description,
            contactName = reminder.contactName,
            dateTimeEvent = dateTime
        )
}