package ru.cherepanovk.core_db_impl.data

import kotlinx.coroutines.flow.Flow
import ru.cherepanovk.core_db_api.data.DbApi
import ru.cherepanovk.core_db_api.data.Reminder
import ru.cherepanovk.core_db_impl.ReminderEntityMaper
import ru.cherepanovk.core_db_impl.data.olddb.LocalBase
import ru.cherepanovk.core_db_impl.data.room.CallBackLtDb
import ru.cherepanovk.core_db_impl.data.room.entities.ReminderEntity
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


class DbApiImpl @Inject constructor(
    private val localBase: LocalBase,
    private val callBackLtDb: CallBackLtDb,
    private val mapper: ReminderEntityMaper
) : DbApi {

    override suspend fun saveReminders(reminders: List<Reminder>) {
        val entities = reminders.map { mapper.map(it) }
        callBackLtDb.getReminderDao().insertReminders(entities)
    }

    override fun getRemindersFromOldDb(): List<Reminder> {
        return localBase.allEvents
    }

    override suspend fun getAllReminders(): List<Reminder> {
        return callBackLtDb.getReminderDao().getAllReminders()
    }

    override fun getRemindersBetweenDates(startDate: Date, endDate: Date): Flow<List<Reminder>> {
        return callBackLtDb.getReminderDao().getRemindersBetweenDates(startDate.time, endDate.time)
    }

    override suspend fun getYears(): List<String> {
        return callBackLtDb.getReminderDao().getYears()
    }

    override suspend fun getReminderById(id: String): Reminder {
        return callBackLtDb.getReminderDao().getReminderById(id)
    }

    override suspend fun saveReminder(reminder: Reminder) {
        callBackLtDb.getReminderDao().insertReminder(mapper.map(reminder))
    }

    override suspend fun deleteReminderById(id: String) {
        callBackLtDb.getReminderDao().deleteReminderById(id)
    }
}