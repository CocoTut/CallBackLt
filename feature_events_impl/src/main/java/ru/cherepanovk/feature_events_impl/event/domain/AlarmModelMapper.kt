package ru.cherepanovk.feature_events_impl.event.domain

import ru.cherepanovk.core_db_api.data.Reminder
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
}