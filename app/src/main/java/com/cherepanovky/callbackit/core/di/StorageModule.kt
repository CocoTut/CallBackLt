package com.cherepanovky.callbackit.core.di

import android.content.Context
import android.preference.PreferenceManager
import com.cherepanovky.callbackit.core.storage.PreferencesManager
import com.cherepanovky.callbackit.core.storage.olddb.DbHelper
import com.cherepanovky.callbackit.core.storage.olddb.LocalBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class StorageModule {
    @Provides
    @Singleton
    fun providePreferencesManager(context: Context ): PreferencesManager =
        PreferencesManager(PreferenceManager.getDefaultSharedPreferences(context))

//    @Provides
//    @Singleton
//    fun provideOtpDataBase(context: Context): OtpDatabase =
//        Room.databaseBuilder(context, OtpDatabase::class.java, "otpDB").build()

    @Provides
    @Singleton
    fun provideDbHelper(context: Context) = DbHelper(context)

    @Provides
    @Singleton
    fun provideOldDataBase(dbHelper: DbHelper): LocalBase{
        return LocalBase(dbHelper)
    }
}