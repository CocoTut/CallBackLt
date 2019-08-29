package com.cherepanovky.callbackit.core.di

import android.app.Application
import com.cherepanovky.callbackit.core.di.viewmodel.ViewModelModule
import com.cherepanovky.callbackit.core.di.viewmodel.ViewModelProvider
import dagger.Component
import ru.cherepanovk.core.di.dependencies.ContextProvider

import javax.inject.Singleton

@Singleton
@Component(modules = [
    ApplicationModule::class,
    ViewModelModule::class
])
interface ApplicationComponent : ContextProvider, ViewModelProvider {
    fun inject(application: Application)
}