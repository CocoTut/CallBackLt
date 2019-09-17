package ru.cherepanovk.feature_events_impl.events

import ru.cherepanovk.core.utils.Mapper
import ru.cherepanovk.core_db_api.data.Reminder
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

private const val PATTERN_DATE = "EEE, dd MMM yyyy"
private const val PATTERN_TIME = "HH:mm"

class ItemReminderMapper @Inject constructor() : Mapper<Reminder, ItemReminder> {
    override fun map(from: Reminder): ItemReminder {
        return ItemReminder(
            id = from.id(),
            description = from.description(),
            phoneNumber = from.phoneNumber(),
            date = getDateTime(from.dateTimeEvent(), PATTERN_DATE),
            time = getDateTime(from.dateTimeEvent(), PATTERN_TIME)
        )
    }

    private fun getDateTime(dateTime: Date, pattern: String): String {
        val formatter = SimpleDateFormat(pattern, Locale.getDefault())
        return formatter.format(dateTime)
    }
}