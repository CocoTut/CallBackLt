package ru.cherepanovk.core_db_impl.db.room.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.cherepanovk.core_db_impl.db.room.entities.RescheduleEntity

@Dao
interface RescheduleDao {
    @Query("SELECT * FROM RescheduleEntity")
    fun getAllReschedules(): Flow<List<RescheduleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addReschedules(reschedules: List<RescheduleEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addReschedule(reschedule: RescheduleEntity)


    @Query("DELETE FROM RescheduleEntity WHERE id = :id")
    suspend fun deleteRescheduleById(id: Int)
}