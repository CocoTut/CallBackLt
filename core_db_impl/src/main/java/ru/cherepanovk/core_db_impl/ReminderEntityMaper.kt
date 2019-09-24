package ru.cherepanovk.core_db_impl

import ru.cherepanovk.core.utils.Mapper
import ru.cherepanovk.core_db_api.data.Reminder
import ru.cherepanovk.core_db_impl.data.room.entities.ReminderEntity
import javax.inject.Inject

class ReminderEntityMaper @Inject constructor() : Mapper<Reminder, ReminderEntity> {
    override fun map(from: Reminder): ReminderEntity {
        return ReminderEntity(
            id = from.id(),
            phoneNumber = from.phoneNumber(),
            description = from.description(),
            contactName = from.contactName(),
            dateTimeEvent = from.dateTimeEvent()
        )
    }
}