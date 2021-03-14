package com.cherepanovky.callbackit

import android.app.Application
import com.cherepanovky.callbackit.di.holders.GlobalHoldersFeaturesInitializer
import ru.cherepanovk.core.di.*


class CallBackItApplication : Application() {

    override fun onCreate() {
        initComponentManager()
        super.onCreate()
    }

    private fun initComponentManager() {
        FeatureComponentContainerManager().apply {
            initialize(
                GlobalHoldersFeaturesInitializer(
                    this@CallBackItApplication,
                    this
                )
            )
            DI.init(this)
        }
    }
}