package ru.cherepanovk.core_db_impl.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.cherepanovk.core_db_impl.data.room.entities.ReminderEntity

@Dao
interface ReminderDao {

    @Query("SELECT * FROM ReminderEntity")
    suspend fun getAllReminders(): List<ReminderEntity>

    @Query("SELECT * FROM ReminderEntity WHERE dateTimeEvent BETWEEN :startDate AND :endDate ")
    fun getRemindersBetweenDates(startDate: Long, endDate: Long): Flow<List<ReminderEntity>>

    @Query("SELECT strftime('%Y', dateTimeEvent / 1000, 'unixepoch') as year FROM ReminderEntity GROUP BY year")
    suspend fun getYears(): List<String>

    @Query("SELECT * FROM ReminderEntity WHERE id =:id")
    suspend fun getReminderById(id: String): ReminderEntity

    @Query("DELETE FROM ReminderEntity WHERE id = :id ")
    suspend fun deleteReminderById(id: String)

    @Query("SELECT * FROM ReminderEntity WHERE phoneNumber LIKE :phoneNumber ORDER BY dateTimeEvent DESC LIMIT 1 ")
    suspend fun getReminderByPhoneNumber(phoneNumber: String): ReminderEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReminders(reminders:List<ReminderEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReminder(reminder: ReminderEntity)

}