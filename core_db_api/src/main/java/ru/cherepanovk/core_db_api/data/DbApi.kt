package ru.cherepanovk.core_db_api.data

interface DbApi {

    fun getRemindersFromOldDb(): List<Reminder>

    suspend fun getAllReminders(): List<Reminder>

    suspend fun saveReminders(reminders: List<Reminder>)
}