package ru.cherepanovk.core_domain_api.data

import java.util.*

interface AlarmReminder {
    fun id(): String

    fun description(): String

    fun phoneNumber(): String

    fun contactName(): String

    fun dateTimeEvent(): Date
}