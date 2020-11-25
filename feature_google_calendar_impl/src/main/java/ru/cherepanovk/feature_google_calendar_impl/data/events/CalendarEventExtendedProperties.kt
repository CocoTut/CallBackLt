package ru.cherepanovk.feature_google_calendar_impl.data.events

import com.google.api.services.calendar.model.Event


class CalendarEventExtendedProperties(
    val contactName: String?,
    val phoneNumber: String?
) {
    fun toExtendedProperties() = mapOf(
        PHONE_NUMBER to phoneNumber,
        CONTACT_NAME to contactName
    )



    companion object {
        fun fromExtendedProperties(properties: Event.ExtendedProperties): CalendarEventExtendedProperties =
            CalendarEventExtendedProperties(
                contactName = properties.private[CONTACT_NAME].toString(),
                phoneNumber = properties.private[PHONE_NUMBER].toString()
            )
        private const val PHONE_NUMBER = "phoneNumber"
        private const val CONTACT_NAME = "contactName"
    }
}