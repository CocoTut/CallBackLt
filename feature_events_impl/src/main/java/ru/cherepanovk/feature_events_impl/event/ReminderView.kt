package ru.cherepanovk.feature_events_impl.event

class ReminderView(
    var id: String? = null,
    val description: String,
    val phoneNumber: String,
    val contactName: String,
    val date: String,
    val time: String
)