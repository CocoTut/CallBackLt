package ru.cherepanovk.feature_events_impl.event

import ru.cherepanovk.core.utils.Mapper
import ru.cherepanovk.core_db_api.data.Reminder
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

private const val dateFormat = "EEE, dd MMM yyyy"
private const val timeFormat = "HH:mm"

class ReminderViewMapper @Inject constructor(): Mapper<Reminder, ReminderView> {
    override fun map(from: Reminder): ReminderView {
       return ReminderView(
           id = from.id(),
           description = from.description(),
           phoneNumber = from.phoneNumber(),
           contactName = from.contactName(),
           date = getDateTime(from.dateTimeEvent(), dateFormat),
           time = getDateTime(from.dateTimeEvent(), timeFormat)

       )
    }

    private fun getDateTime(dateTimeEvent: Date, format: String): String {
        val formatter = SimpleDateFormat(format, Locale.getDefault())
        return formatter.format(dateTimeEvent)
    }
}