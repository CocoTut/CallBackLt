package com.cherepanovky.callbackit.di

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.Multibinds
import javax.inject.Singleton

@Module(includes = [ApplicationModule.Bindings::class])
class ApplicationModule(private val application: Application) {

    @Provides
    @Singleton
    fun provideApplicationContext(): Context = application

    @Module
    interface Bindings {
        @Multibinds
        fun provideViewModelsMap(): Map<Class<out ViewModel>, ViewModel>
    }

}