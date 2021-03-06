package com.cherepanovky.callbackit.di

import android.app.Application
import dagger.Component
import ru.cherepanovk.core.di.dependencies.AppConfigProvider
import ru.cherepanovk.core.di.dependencies.ContextProvider
import ru.cherepanovk.core.di.dependencies.GooglePlayServicesAvailabilityProvider

import javax.inject.Singleton

@Singleton
@Component(modules = [
    ApplicationModule::class
])
interface ApplicationComponent : ContextProvider, AppConfigProvider, GooglePlayServicesAvailabilityProvider {
    fun inject(application: Application)
}