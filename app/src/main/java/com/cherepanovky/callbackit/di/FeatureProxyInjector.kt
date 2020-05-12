package com.cherepanovky.callbackit.di

import ru.cherepanovk.core.di.ComponentManager
import ru.cherepanovk.core.di.getOrThrow
import ru.cherepanovk.core_db_api.di.CoreDbApi
import ru.cherepanovk.core_db_impl.di.DaggerCoreDbComponent
import ru.cherepanovk.core_preferences_impl.di.DaggerCorePreferencesComponent
import ru.cherepanovk.feature_alarm_api.di.CoreDomainApi

import ru.cherepanovk.feature_alarm_impl.di.DaggerCoreDomainComponent
import ru.cherepanovk.feature_events_api.EventsFeatureApi
import ru.cherepanovk.feature_events_impl.events.di.DaggerEventsComponent
import ru.cherepanovk.feature_google_calendar_impl.di.DaggerGoogleCalendarApiComponent
import ru.cherepanovk.feature_settings_api.SettingsFeatureApi
import ru.cherepanovk.feature_settings_impl.di.DaggerSettingsComponent


class FeatureProxyInjector {
    companion object {
        fun getEventsFeature(): EventsFeatureApi {
            return DaggerEventsComponent.builder()
                .contextProvider(ComponentManager.getOrThrow())
                .rootViewProvider(ComponentManager.getOrThrow())
                .coreDbApi(
                    if (ComponentManager.has(CoreDbApi::class))
                        ComponentManager.getOrThrow()
                    else
                        DaggerCoreDbComponent.builder()
                            .contextProvider(ComponentManager.getOrThrow())
                            .build()
                            .also { ComponentManager.put(it) }
                )
                .coreDomainApi(
                    if (ComponentManager.has(CoreDomainApi::class))
                        ComponentManager.getOrThrow()
                    else
                        DaggerCoreDomainComponent.builder()
                            .contextProvider(ComponentManager.getOrThrow())
                            .build()
                            .also { ComponentManager.put(it) }
                )
                .corePreferencesApi(
                    DaggerCorePreferencesComponent.builder()
                        .contextProvider(ComponentManager.getOrThrow())
                        .build()
                        .also { ComponentManager.put(it) }
                )
                .coreGoogleCalendarApi(
                    DaggerGoogleCalendarApiComponent.builder()
                        .contextProvider(ComponentManager.getOrThrow())
                        .corePreferencesApi(ComponentManager.getOrThrow())
                        .build()
                        .also { ComponentManager.put(it) }
                )
                .build()
                .also { ComponentManager.put(it) }
        }

        fun getSettingsFeature(): SettingsFeatureApi {
            return DaggerSettingsComponent.builder()
                .corePreferencesApi(
                    DaggerCorePreferencesComponent.builder()
                        .contextProvider(ComponentManager.getOrThrow())
                        .build()
                )
                .contextProvider(ComponentManager.getOrThrow())
                .rootViewProvider(ComponentManager.getOrThrow())
                .coreGoogleCalendarApi(
                    ComponentManager.getOrThrow()
                )
                .build()
                .also { ComponentManager.put(it) }
        }
    }
}