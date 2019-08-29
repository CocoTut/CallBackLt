package ru.cherepanovk.core_db_impl.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.cherepanovk.core_db_impl.data.room.entities.ReminderEntity

@Dao
interface ReminderDao {

    @Query("SELECT * FROM ReminderEntity")
    suspend fun getAllReminders(): List<ReminderEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReminders(reminders:List<ReminderEntity>)

}