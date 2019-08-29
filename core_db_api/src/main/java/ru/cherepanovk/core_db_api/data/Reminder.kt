package ru.cherepanovk.core_db_api.data

import java.util.*

interface Reminder {

    fun id(): String

    fun description(): String

    fun phoneNumber(): String

    fun contactName(): String

    fun dateTimeEvent(): Date
}