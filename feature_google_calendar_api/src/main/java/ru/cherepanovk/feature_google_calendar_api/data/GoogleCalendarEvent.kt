package ru.cherepanovk.feature_google_calendar_api.data

import java.util.*

class GoogleCalendarEvent(
    val id: String,
    val startTime: Date,
    val endTime: Date,
    val contactName: String,
    val phoneNumber: String,
    val description: String
)