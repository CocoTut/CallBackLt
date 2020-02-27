package ru.cherepanovk.core_db_impl.data.room

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object Migrations {
    val MIGRATION_1_2: Migration = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("CREATE TABLE IF NOT EXISTS `Preferences` (`id` INTEGER NOT NULL, `emailAccount` TEXT NOT NULL, `firstStart` INTEGER NOT NULL, `ringtoneUri` TEXT, `observeIncomingCalls` INTEGER NOT NULL, `observeOutgoingCalls` INTEGER NOT NULL, `observeIncomingMissedCalls` INTEGER NOT NULL, PRIMARY KEY(`id`))")
        }
    }
}
