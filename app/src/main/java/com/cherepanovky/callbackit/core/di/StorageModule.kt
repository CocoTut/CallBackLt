package com.cherepanovky.callbackit.core.di

import android.content.Context
import android.preference.PreferenceManager
import com.cherepanovky.callbackit.core.storage.PreferencesManager
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


}