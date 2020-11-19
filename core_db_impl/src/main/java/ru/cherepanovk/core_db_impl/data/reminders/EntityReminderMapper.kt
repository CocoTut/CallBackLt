package ru.cherepanovk.core_db_impl.data.reminders

import ru.cherepanovk.core_db_api.data.models.Reminder
import ru.cherepanovk.core_db_impl.db.room.entities.ReminderEntity
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