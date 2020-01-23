package com.cherepanovky.callbackit

import android.app.Application
import com.cherepanovky.callbackit.di.ApplicationModule
import com.cherepanovky.callbackit.di.DaggerApplicationComponent
import ru.cherepanovk.core.di.ComponentManager


class CallBackItApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        println("CallBackItApplication")
        initComponentManager()
    }

    private fun initComponentManager() {
        DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
            .also { ComponentManager.put(it) }
            .inject(this)
    }
}