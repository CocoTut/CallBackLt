package ru.cherepanovk.feature_events_impl.events

import ru.cherepanovk.core.utils.DateTimeHelper
import ru.cherepanovk.core.utils.Mapper
import ru.cherepanovk.core_db_api.data.Reminder
import javax.inject.Inject

class ItemReminderMapper @Inject constructor(private val dateTimeHelper: DateTimeHelper) : Mapper<Reminder, ItemReminder> {
    override fun map(from: Reminder): ItemReminder {
        return ItemReminder(
            id = from.id,
            description = if (from.description.isBlank()) from.contactName else from.description,
            phoneNumber = from.phoneNumber,
            date = dateTimeHelper.getDateString(from.dateTimeEvent),
            time = dateTimeHelper.getTimeString(from.dateTimeEvent)
        )
    }
}