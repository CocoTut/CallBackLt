package com.cherepanovky.callbackit.core.di

import android.app.Application
import com.cherepanovky.callbackit.core.di.di.viewmodel.ViewModelModule
import com.cherepanovky.callbackit.features.events.di.EventsComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class,ViewModelModule::class, StorageModule::class])
interface ApplicationComponent {
    fun inject(application: Application)

    fun createEventsComponent(): EventsComponent.Builder
}