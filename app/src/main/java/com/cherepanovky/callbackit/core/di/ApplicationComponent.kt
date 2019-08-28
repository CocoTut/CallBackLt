package com.cherepanovky.callbackit.core.di

import android.app.Application
import dagger.Component
import ru.cherepanovk.core.di.dependencies.ContextProvider

import javax.inject.Singleton

@Singleton
@Component(modules = [
    ApplicationModule::class
])
interface ApplicationComponent : ContextProvider {
    fun inject(application: Application)
}