package ru.cherepanovk.core_db_impl.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.cherepanovk.core_db_impl.data.room.entities.ReminderEntity
import java.util.*

@Dao
interface ReminderDao {

    @Query("SELECT * FROM ReminderEntity")
    suspend fun getAllReminders(): List<ReminderEntity>

    @Query("SELECT * FROM ReminderEntity WHERE dateTimeEvent BETWEEN :startDate AND :endDate ")
    suspend fun getRemindersBetweenDates(startDate: Long, endDate: Long): List<ReminderEntity>

    @Query("SELECT strftime('%Y', dateTimeEvent / 1000, 'unixepoch') as year FROM ReminderEntity GROUP BY year")
    suspend fun getYears(): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReminders(reminders:List<ReminderEntity>)

}