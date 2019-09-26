package ru.cherepanovk.feature_events_impl.event

import ru.cherepanovk.core.utils.DateTimeHelper
import ru.cherepanovk.core.utils.Mapper
import java.util.*
import javax.inject.Inject

class NewReminderMapper @Inject constructor(private val dateTimeHelper: DateTimeHelper) : Mapper<ReminderView, NewReminder> {
    override fun map(from: ReminderView): NewReminder {
        return NewReminder(
            id = from.id ?: UUID.randomUUID().toString(),
            phoneNumber = from.phoneNumber,
            description = from.description,
            contactName = from.contactName,
            dateTimeEvent = dateTimeHelper.getDateFromDateTimeString(from.date, from.time)

        )
    }
}