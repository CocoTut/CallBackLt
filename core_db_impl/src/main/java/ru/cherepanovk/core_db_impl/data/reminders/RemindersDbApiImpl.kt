package ru.cherepanovk.core_db_impl.data.reminders

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.cherepanovk.core_db_api.data.RemindersDbApi
import ru.cherepanovk.core_db_api.data.models.Reminder
import ru.cherepanovk.core_db_impl.db.olddb.LocalBase
import ru.cherepanovk.core_db_impl.db.room.dao.ReminderDao
import ru.cherepanovk.core_db_impl.db.room.entities.PHONE_REGEX_PATTERN
import java.util.*
import javax.inject.Inject


class RemindersDbApiImpl @Inject constructor(
    private val localBase: LocalBase,
    private val reminderDao: ReminderDao,
    private val mapper: ReminderEntityMaper,
    private val entityReminderMapper: EntityReminderMapper
) : RemindersDbApi {

    override suspend fun saveReminders(reminders: List<Reminder>) {
        val entities = reminders.map { mapper.map(it) }
        reminderDao.insertReminders(entities)
    }

    override fun getRemindersFromOldDb(): List<Reminder> {
        return localBase.allEvents
    }

    override fun getGoogleAccountFromOldDb(): String? {
        return localBase.account?.account
    }

    override suspend fun getAllReminders(): List<Reminder> {
        return reminderDao.getAllReminders()
            .map {
                entityReminderMapper.map(it)
            }
    }

    override fun getRemindersBetweenDates(startDate: Date, endDate: Date): Flow<List<Reminder>> {
        return reminderDao.getRemindersBetweenDates(startDate.time, endDate.time)
            .map { entities ->
                entities.map { entityReminderMapper.map(it) }
            }
    }

    override fun getYears(): Flow<List<String>>  {
        return reminderDao.getYears()
    }

    override suspend fun getReminderById(id: String): Reminder? {
        return reminderDao.getReminderById(id)?.let {
            entityReminderMapper.map(it)
        }
    }

    override suspend fun saveReminder(reminder: Reminder): Reminder {
        return mapper.map(reminder).run {
            reminderDao.insertReminder(this)
            return@run getReminderById(this.id)!!
        }

    }

    override suspend fun updateReminder(reminder: Reminder) {
        reminderDao.updateReminder(mapper.map(reminder))
    }

    override suspend fun deleteReminderById(id: String) {
        reminderDao.deleteReminderById(id)
    }

    override suspend fun getReminderByPhoneNumber(phoneNumber: String): Reminder? {
        return reminderDao.getReminderByPhoneNumber(
                Regex(PHONE_REGEX_PATTERN).replace(phoneNumber, "")
            )?.let { entityReminderMapper.map(it) }
    }

    override suspend fun getRemindersAfterDate(dateAfter: Date): List<Reminder> {
        return reminderDao.getRemindersAfterDate(dateAfter.time)
            .map { entityReminderMapper.map(it) }

    }

}