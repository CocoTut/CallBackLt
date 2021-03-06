package com.cherepanovky.callbackit.di

import android.app.Application
import android.content.Context
import com.cherepanovky.callbackit.config.AppConfigImpl
import com.cherepanovky.callbackit.config.GooglePlayServicesAvailabilityImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.cherepanovk.core.config.AppConfig
import ru.cherepanovk.core.config.GooglePlayServicesAvailability
import javax.inject.Singleton

@Module(includes = [ApplicationModule.Bindings::class])
class ApplicationModule(private val application: Application) {

    @Provides
    @Singleton
    fun provideApplicationContext(): Context = application

    @Module
    abstract class Bindings {
        @Binds
        @Singleton
        abstract fun bindAppConfig(appConfigImpl: AppConfigImpl): AppConfig

        @Binds
        @Singleton
        abstract fun bindGooglePlayServicesAvailability(
            googlePlayServicesAvailability: GooglePlayServicesAvailabilityImpl
        ): GooglePlayServicesAvailability
    }
}