package ru.cherepanovk.core_db_impl.db.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.cherepanovk.core_db_impl.db.room.dao.ReminderDao
import ru.cherepanovk.core_db_impl.db.room.dao.RescheduleDao
import ru.cherepanovk.core_db_impl.db.room.entities.ReminderEntity
import ru.cherepanovk.core_db_impl.db.room.entities.RescheduleEntity

@Database(entities = [ReminderEntity::class, RescheduleEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class CallBackLtDb : RoomDatabase() {

    abstract fun getReminderDao(): ReminderDao

    abstract fun getRescheduleDao(): RescheduleDao
}