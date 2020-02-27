package ru.cherepanovk.core_db_api.data

import kotlinx.coroutines.flow.Flow
import java.util.*

interface DbApi {

    fun getRemindersFromOldDb(): List<Reminder>

    suspend fun getAllReminders(): List<Reminder>

    suspend fun saveReminders(reminders: List<Reminder>)

//    suspend fun getRemindersBetweenDates(startDate: Date, endDate: Date): List<Reminder>

    fun getRemindersBetweenDates(startDate: Date, endDate: Date): Flow<List<Reminder>>

    suspend fun getYears(): List<String>

    suspend fun getReminderById(id: String): Reminder?

    suspend fun saveReminder(reminder: Reminder)

    suspend fun updateReminder(reminder: Reminder)

    suspend fun deleteReminderById(id: String)

    suspend fun getReminderByPhoneNumber(phoneNumber: String): Reminder?

    suspend fun isFirstStart(): Boolean
}