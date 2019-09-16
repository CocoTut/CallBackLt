package ru.cherepanovk.core_db_api.data

import java.util.*

interface DbApi {

    fun getRemindersFromOldDb(): List<Reminder>

    suspend fun getAllReminders(): List<Reminder>

    suspend fun saveReminders(reminders: List<Reminder>)

    suspend fun getRemindersBetweenDates(startDate: Date, endDate: Date): List<Reminder>

    suspend fun getYears(): List<String>
}