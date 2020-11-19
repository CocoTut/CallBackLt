package ru.cherepanovk.core_db_impl.di

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.cherepanovk.core_db_api.data.RemindersDbApi
import ru.cherepanovk.core_db_api.data.RescheduleDbApi
import ru.cherepanovk.core_db_impl.data.reminders.RemindersDbApiImpl
import ru.cherepanovk.core_db_impl.db.olddb.DbHelper
import ru.cherepanovk.core_db_impl.db.room.CallBackLtDb
import ru.cherepanovk.core_db_impl.data.reschedules.RescheduleDbApiImpl
import ru.cherepanovk.core_db_impl.db.room.dao.ReminderDao
import ru.cherepanovk.core_db_impl.db.room.dao.RescheduleDao
import javax.inject.Singleton

@Module
abstract class CoreDbModule {
    @Singleton
    @Binds
    abstract fun bindReminders(dbApiImpl: RemindersDbApiImpl): RemindersDbApi

    @Singleton
    @Binds
    abstract fun bindReschedule(rescheduleDbApiImpl: RescheduleDbApiImpl): RescheduleDbApi

    companion object {
        @Provides
        @Singleton
        fun provideDbHelper(context: Context) = DbHelper(context)

        @Provides
        @Singleton
        fun provideDb(context: Context): CallBackLtDb =
            Room.databaseBuilder(context, CallBackLtDb::class.java, "CallBackLtDb")
                .build()

        @Provides
        @Singleton
        fun provideRemindersDao(db: CallBackLtDb): ReminderDao = db.getReminderDao()

        @Provides
        @Singleton
        fun provideRescheduleDao(db: CallBackLtDb): RescheduleDao = db.getRescheduleDao()
    }
}

