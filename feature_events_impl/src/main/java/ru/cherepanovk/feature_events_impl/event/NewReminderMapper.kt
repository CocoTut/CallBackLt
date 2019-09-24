package ru.cherepanovk.feature_events_impl.event

import ru.cherepanovk.core.utils.Mapper
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class NewReminderMapper @Inject constructor() : Mapper<ReminderView, NewReminder> {
    override fun map(from: ReminderView): NewReminder {
        return NewReminder(
            id = from.id ?: UUID.randomUUID().toString(),
            phoneNumber = from.phoneNumber,
            description = from.description,
            contactName = from.contactName,
            dateTimeEvent = getDate(from.date, from.time)

        )
    }

    private fun getDate(date: String, time: String): Date {
        val pattern = "$EVENT_DATE_FORMAT $EVENT_TIME_FORMAT"
        val formatter = SimpleDateFormat(pattern, Locale.getDefault())
        val eventDate = "$date $time"
        return formatter.parse(eventDate)
    }
}