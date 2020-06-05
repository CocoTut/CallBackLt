package ru.cherepanovk.feature_events_impl.event

import ru.cherepanovk.core.utils.DateTimeHelper
import ru.cherepanovk.core.utils.Mapper
import ru.cherepanovk.core_db_api.data.Reminder
import javax.inject.Inject


class ReminderViewMapper @Inject constructor(
    private val dateTimeHelper: DateTimeHelper
) {
    fun map(from: Reminder): ReminderView {
        return ReminderView(
            id = from.id,
            description = from.description,
            phoneNumber = from.phoneNumber,
            contactName = from.contactName,
            date = dateTimeHelper.getDateString(from.dateTimeEvent),
            time = dateTimeHelper.getTimeString(from.dateTimeEvent)
        )
    }
}