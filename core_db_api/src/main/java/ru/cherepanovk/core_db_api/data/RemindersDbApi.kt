package ru.cherepanovk.core_db_api.data

import kotlinx.coroutines.flow.Flow
import ru.cherepanovk.core_db_api.data.models.Reminder
import java.util.*

interface RemindersDbApi {

    fun getRemindersFromOldDb(): List<Reminder>

    fun getGoogleAccountFromOldDb(): String?

    suspend fun getAllReminders(): List<Reminder>

    suspend fun saveReminders(reminders: List<Reminder>)

    fun getRemindersBetweenDates(startDate: Date, endDate: Date): Flow<List<Reminder>>

    fun getYears(): Flow<List<String>>

    suspend fun getReminderById(id: String): Reminder?

    suspend fun saveReminder(reminder: Reminder): Reminder

    suspend fun updateReminder(reminder: Reminder)

    suspend fun deleteReminderById(id: String)

    suspend fun getReminderByPhoneNumber(phoneNumber: String): Reminder?

    suspend fun getRemindersAfterDate(dateAfter: Date): List<Reminder>

}