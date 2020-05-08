package ru.cherepanovk.core_db_impl.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.cherepanovk.core_db_api.data.DbApi
import ru.cherepanovk.core_db_impl.data.room.dao.PreferencesDao
import ru.cherepanovk.core_db_impl.data.room.dao.ReminderDao
import ru.cherepanovk.core_db_impl.data.room.entities.Preferences
import ru.cherepanovk.core_db_impl.data.room.entities.ReminderEntity

@Database(entities = [ReminderEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class CallBackLtDb : RoomDatabase() {

    abstract fun getReminderDao(): ReminderDao
}