package com.cherepanovky.callbackit

import android.app.Application
import com.cherepanovky.callbackit.core.di.ComponentManager


class CallBackItApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        ComponentManager.init(this)
        ComponentManager.appComponent.inject(this)
    }
}