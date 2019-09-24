package ru.cherepanovk.feature_events_impl.event

import ru.cherepanovk.core.utils.Mapper
import ru.cherepanovk.core_db_api.data.Reminder
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class ReminderViewMapper @Inject constructor(): Mapper<Reminder, ReminderView> {
    override fun map(from: Reminder): ReminderView {
       return ReminderView(
           id = from.id(),
           description = from.description(),
           phoneNumber = from.phoneNumber(),
           contactName = from.contactName(),
           date = getDateTime(from.dateTimeEvent(), EVENT_DATE_FORMAT),
           time = getDateTime(from.dateTimeEvent(), EVENT_TIME_FORMAT)

       )
    }

    private fun getDateTime(dateTimeEvent: Date, format: String): String {
        val formatter = SimpleDateFormat(format, Locale.getDefault())
        return formatter.format(dateTimeEvent)
    }
}