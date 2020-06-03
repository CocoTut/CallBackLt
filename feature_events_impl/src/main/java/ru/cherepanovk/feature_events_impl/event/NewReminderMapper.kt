package ru.cherepanovk.feature_events_impl.event

import ru.cherepanovk.core.utils.DateTimeHelper
import ru.cherepanovk.core_db_api.data.Reminder
import java.util.*
import javax.inject.Inject

class NewReminderMapper @Inject constructor(
    private val dateTimeHelper: DateTimeHelper
) {
    fun map(from: ReminderView): Reminder {
        return Reminder(
            id = from.id ?: "",
            phoneNumber = from.phoneNumber,
            description = from.description,
            contactName = from.contactName,
            dateTimeEvent = dateTimeHelper.getDateFromDateTimeString(from.date, from.time)
        )
    }
}