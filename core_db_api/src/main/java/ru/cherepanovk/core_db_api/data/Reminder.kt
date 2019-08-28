package ru.cherepanovk.core_db_api.data

import java.util.*

interface Reminder {

    fun getId(): String

    fun getDescription(): String

    fun getPhoneNumber(): String

    fun getContactName(): String

    fun getDateTimeEvent(): Date
}