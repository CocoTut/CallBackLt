package com.cherepanovky.callbackit

import android.app.Application
import com.cherepanovky.callbackit.di.ApplicationModule
import com.cherepanovky.callbackit.di.DaggerApplicationComponent
import ru.cherepanovk.core.di.ComponentManager
import ru.cherepanovk.core.di.getOrThrow
import ru.cherepanovk.core_db_impl.di.DaggerCoreDbComponent
import ru.cherepanovk.core_preferences_impl.di.DaggerCorePreferencesComponent
import ru.cherepanovk.feature_alarm_impl.di.DaggerFeatureAlarmComponent
import ru.cherepanovk.feature_google_calendar_impl.di.DaggerGoogleCalendarApiComponent


class CallBackItApplication : Application() {
    override fun onCreate() {
        initComponentManager()
        super.onCreate()
    }

    private fun initComponentManager() {
        DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
            .also {
                ComponentManager.put(it)
            }
            .inject(this)

        DaggerCoreDbComponent.builder()
            .contextProvider(ComponentManager.getOrThrow())
            .build()
            .also { coreDbComponent -> ComponentManager.put(coreDbComponent) }

        DaggerCorePreferencesComponent.builder()
            .contextProvider(ComponentManager.getOrThrow())
            .build()
            .also { coreDbComponent -> ComponentManager.put(coreDbComponent) }

        DaggerFeatureAlarmComponent.builder()
            .contextProvider(ComponentManager.getOrThrow())
            .build()
            .also { ComponentManager.put(it) }

        DaggerGoogleCalendarApiComponent.builder()
            .contextProvider(ComponentManager.getOrThrow())
            .corePreferencesApi(ComponentManager.getOrThrow())
            .coreDbApi(ComponentManager.getOrThrow())
            .featureAlarmApi(ComponentManager.getOrThrow())
            .build()
            .also { ComponentManager.put(it) }
    }
}