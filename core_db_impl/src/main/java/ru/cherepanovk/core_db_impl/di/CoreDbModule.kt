package ru.cherepanovk.core_db_impl.di

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.cherepanovk.core_db_api.data.DbApi
import ru.cherepanovk.core_db_impl.data.DbApiImpl
import ru.cherepanovk.core_db_impl.data.olddb.DbHelper
import ru.cherepanovk.core_db_impl.data.room.CallBackLtDb
import ru.cherepanovk.core_db_impl.data.room.Migrations
import javax.inject.Singleton

@Module
abstract class CoreDbModule {
    @Singleton
    @Binds
    abstract fun bindOldDb(dbApiImpl: DbApiImpl): DbApi

    companion object {
        @Provides
        @Singleton
        fun provideDbHelper(context: Context) = DbHelper(context)

        @Provides
        @Singleton
        fun provideDb(context: Context): CallBackLtDb =
            Room.databaseBuilder(context, CallBackLtDb::class.java, "CallBackLtDb")
                .build()
    }
}

