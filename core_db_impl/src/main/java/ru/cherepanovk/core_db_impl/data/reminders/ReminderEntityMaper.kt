package ru.cherepanovk.core_db_impl.data.reminders

import ru.cherepanovk.core.utils.Mapper
import ru.cherepanovk.core_db_api.data.models.Reminder
import ru.cherepanovk.core_db_impl.db.room.entities.PHONE_REGEX_PATTERN
import ru.cherepanovk.core_db_impl.db.room.entities.ReminderEntity
import java.util.*
import javax.inject.Inject

class ReminderEntityMaper @Inject constructor() : Mapper<Reminder, ReminderEntity> {
    override fun map(from: Reminder): ReminderEntity {
        return ReminderEntity(
            id = if(from.id.isEmpty()) UUID.randomUUID().toString().replace("-","") else from.id,
            phoneNumber = from.phoneNumber,
            description = from.description,
            contactName = from.contactName,
            dateTimeEvent = from.dateTimeEvent,
            phoneNumberSearch = Regex(PHONE_REGEX_PATTERN).replace(from.phoneNumber,"")
        )
    }
}