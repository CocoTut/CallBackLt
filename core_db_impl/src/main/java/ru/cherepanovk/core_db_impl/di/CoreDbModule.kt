package ru.cherepanovk.core_db_impl.di

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.cherepanovk.core_db_api.data.OldDbClientApi
import ru.cherepanovk.core_db_impl.data.olddb.DbHelper
import ru.cherepanovk.core_db_impl.data.olddb.LocalBase
import javax.inject.Singleton

@Module(includes = [DbHelperModule::class])
abstract class CoreDbModule {
    @Singleton
    @Binds
    abstract fun bindOldDb(localBase: LocalBase): OldDbClientApi
}

@Module
object DbHelperModule {
    @JvmStatic
    @Provides
    @Singleton
    fun provideDbHelper(context: Context) = DbHelper(context)
}