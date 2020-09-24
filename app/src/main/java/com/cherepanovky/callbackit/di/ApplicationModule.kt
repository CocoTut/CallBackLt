package com.cherepanovky.callbackit.di

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cherepanovky.callbackit.config.AppConfigImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.Multibinds
import ru.cherepanovk.core.config.AppConfig
import ru.cherepanovk.core.di.viewmodel.ViewModelFactory
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
    }
}