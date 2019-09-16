package ru.cherepanovk.core_db_impl.data

import ru.cherepanovk.core_db_api.data.DbApi
import ru.cherepanovk.core_db_api.data.Reminder
import ru.cherepanovk.core_db_impl.data.olddb.LocalBase
import ru.cherepanovk.core_db_impl.data.room.CallBackLtDb
import ru.cherepanovk.core_db_impl.data.room.entities.ReminderEntity
import java.util.*
import javax.inject.Inject

class DbApiImpl @Inject constructor(
    private val localBase: LocalBase,
    private val callBackLtDb: CallBackLtDb
) : DbApi {

    override suspend fun saveReminders(reminders: List<Reminder>) {
        val entities = reminders.map { ReminderEntity(
            id = it.id(),
            phoneNumber = it.phoneNumber(),
            description = it.description(),
            contactName = it.contactName(),
            dateTimeEvent = it.dateTimeEvent()
        ) }
        callBackLtDb.getReminderDao().insertReminders(entities)
    }

    override fun getRemindersFromOldDb(): List<Reminder> {
        return localBase.allEvents
    }

    override suspend fun getAllReminders(): List<Reminder> {
        return callBackLtDb.getReminderDao().getAllReminders()
    }

    override suspend fun getRemindersBetweenDates(startDate: Date, endDate: Date): List<Reminder> {
        return callBackLtDb.getReminderDao().getRemindersBetweenDates(startDate.time, endDate.time)
    }

    override suspend fun getYears(): List<String> {
        return callBackLtDb.getReminderDao().getYears()
    }
}