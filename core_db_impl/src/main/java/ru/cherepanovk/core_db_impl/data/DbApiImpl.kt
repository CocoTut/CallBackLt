package ru.cherepanovk.core_db_impl.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.cherepanovk.core_db_api.data.DbApi
import ru.cherepanovk.core_db_api.data.Reminder
import ru.cherepanovk.core_db_impl.EntityReminderMapper
import ru.cherepanovk.core_db_impl.ReminderEntityMaper
import ru.cherepanovk.core_db_impl.data.olddb.LocalBase
import ru.cherepanovk.core_db_impl.data.room.CallBackLtDb
import ru.cherepanovk.core_db_impl.data.room.entities.PHONE_REGEX_PATTERN
import ru.cherepanovk.core_db_impl.data.room.entities.ReminderEntity
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


class DbApiImpl @Inject constructor(
    private val localBase: LocalBase,
    private val callBackLtDb: CallBackLtDb,
    private val mapper: ReminderEntityMaper,
    private val entityReminderMapper: EntityReminderMapper
) : DbApi {

    override suspend fun saveReminders(reminders: List<Reminder>) {
        val entities = reminders.map { mapper.map(it) }
        callBackLtDb.getReminderDao().insertReminders(entities)
    }

    override fun getRemindersFromOldDb(): List<Reminder> {
        return localBase.allEvents
    }

    override fun getGoogleAccountFromOldDb(): String? {
        return localBase.account?.account
    }

    override suspend fun getAllReminders(): List<Reminder> {
        return callBackLtDb.getReminderDao()
            .getAllReminders()
            .map {
                entityReminderMapper.map(it)
            }
    }

    override fun getRemindersBetweenDates(startDate: Date, endDate: Date): Flow<List<Reminder>> {
        return callBackLtDb.getReminderDao()
            .getRemindersBetweenDates(startDate.time, endDate.time)
            .map { entities ->
                entities.map { entityReminderMapper.map(it) }
            }
    }

    override suspend fun getYears(): List<String> {
        return callBackLtDb.getReminderDao().getYears()
    }

    override suspend fun getReminderById(id: String): Reminder? {
        return callBackLtDb.getReminderDao().getReminderById(id)?.let {
            entityReminderMapper.map(it)
        }
    }

    override suspend fun saveReminder(reminder: Reminder): Reminder {
        return mapper.map(reminder).run {
            callBackLtDb.getReminderDao().insertReminder(this)
            return@run getReminderById(this.id)!!
        }

    }

    override suspend fun updateReminder(reminder: Reminder) {
        callBackLtDb.getReminderDao().updateReminder(mapper.map(reminder))
    }

    override suspend fun deleteReminderById(id: String) {
        callBackLtDb.getReminderDao().deleteReminderById(id)
    }

    override suspend fun getReminderByPhoneNumber(phoneNumber: String): Reminder? {
        return callBackLtDb.getReminderDao()
            .getReminderByPhoneNumber(
                Regex(PHONE_REGEX_PATTERN).replace(phoneNumber, "")
            )?.let { entityReminderMapper.map(it) }
    }

    override suspend fun getRemindersAfterDate(dateAfter: Date): List<Reminder> {
        return callBackLtDb.getReminderDao()
            .getRemindersAfterDate(dateAfter.time)
            .map { entityReminderMapper.map(it) }

    }

}