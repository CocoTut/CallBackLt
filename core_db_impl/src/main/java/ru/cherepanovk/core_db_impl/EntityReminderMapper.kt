package ru.cherepanovk.core_db_impl

import ru.cherepanovk.core_db_api.data.Reminder
import ru.cherepanovk.core_db_impl.data.room.entities.ReminderEntity
import javax.inject.Inject

class EntityReminderMapper @Inject constructor() {
    fun map(from: ReminderEntity): Reminder {
        return Reminder(
            id = from.id,
            phoneNumber = from.phoneNumber,
            description = from.description,
            contactName = from.contactName,
            dateTimeEvent = from.dateTimeEvent
        )
    }
}