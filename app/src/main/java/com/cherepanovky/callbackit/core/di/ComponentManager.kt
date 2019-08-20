package com.cherepanovky.callbackit.core.di

import android.app.Application

object ComponentManager {

    lateinit var appComponent: ApplicationComponent
        private set

    fun init(application: Application) {
        if (this::appComponent.isInitialized) throw IllegalStateException("Already initialized")

        appComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(application))
            .build()
    }
}