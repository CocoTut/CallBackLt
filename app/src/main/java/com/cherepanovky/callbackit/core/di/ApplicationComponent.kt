package com.cherepanovky.callbackit.core.di

import android.app.Application
import com.cherepanovky.callbackit.core.di.di.viewmodel.ViewModelModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class,ViewModelModule::class, StorageModule::class])
interface ApplicationComponent {
    fun inject(application: Application)

}