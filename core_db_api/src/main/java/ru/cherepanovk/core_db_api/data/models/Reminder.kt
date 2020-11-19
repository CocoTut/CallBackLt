package ru.cherepanovk.core_db_api.data.models

import java.util.*

class Reminder(

    val id: String,

    val description: String,

    val phoneNumber: String,

    val contactName: String,

    val dateTimeEvent: Date
)