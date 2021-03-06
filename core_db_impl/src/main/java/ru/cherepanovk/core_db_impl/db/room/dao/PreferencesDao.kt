package ru.cherepanovk.core_db_impl.db.room.dao

import androidx.room.Dao
import androidx.room.Query

@Dao
interface PreferencesDao {

    @Query("SELECT firstStart FROM Preferences")
    suspend fun isFirstStar(): Boolean?
}