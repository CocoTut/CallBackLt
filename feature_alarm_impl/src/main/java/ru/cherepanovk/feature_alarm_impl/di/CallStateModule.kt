package ru.cherepanovk.feature_alarm_impl.di

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CallStateModule(private val context: Context) {
    @Provides
    @Singleton
    fun provideApplicationContext(): Context = context
}