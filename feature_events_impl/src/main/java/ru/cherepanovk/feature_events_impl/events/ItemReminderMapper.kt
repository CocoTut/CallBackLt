package ru.cherepanovk.feature_events_impl.events

import ru.cherepanovk.core.utils.DateTimeHelper
import ru.cherepanovk.core.utils.Mapper
import ru.cherepanovk.core_db_api.data.models.Reminder
import javax.inject.Inject

class ItemReminderMapper @Inject constructor(private val dateTimeHelper: DateTimeHelper) :
    Mapper<Reminder, ItemReminder> {
    override fun map(from: Reminder): ItemReminder {
        return ItemReminder(
            id = from.id,
            description = from.description,
            contactName = from.contactName,
            phoneNumber = from.phoneNumber,
            date = dateTimeHelper.getDateString(from.dateTimeEvent),
            time = dateTimeHelper.getTimeString(from.dateTimeEvent),
            dateContentDescription = dateTimeHelper.getFullDateString(from.dateTimeEvent)
        )
    }


}