package com.cherepanovky.callbackit.di

import ru.cherepanovk.core.di.ComponentManager
import ru.cherepanovk.core.di.getOrThrow
import ru.cherepanovk.core_db_impl.di.DaggerCoreDbComponent
import ru.cherepanovk.core_domain_impl.di.DaggerCoreDomainComponent
import ru.cherepanovk.feature_events_api.EventsFeatureApi
import ru.cherepanovk.feature_events_impl.events.di.DaggerEventsComponent
import ru.cherepanovk.feature_settings_api.SettingsFeatureApi
import ru.cherepanovk.feature_settings_impl.di.DaggerSettingsComponent


class FeatureProxyInjector {
    companion object {
        fun getEventsFeature(): EventsFeatureApi{
            return DaggerEventsComponent.builder()
                .contextProvider(ComponentManager.getOrThrow())
                .rootViewProvider(ComponentManager.getOrThrow())
                .coreDbApi(
                    DaggerCoreDbComponent.builder()
                        .contextProvider(ComponentManager.getOrThrow())
                        .build()
                        .also { ComponentManager.put(it) }
                )
                .coreDomainApi(DaggerCoreDomainComponent.builder()
                    .contextProvider(ComponentManager.getOrThrow())
                    .build()
                    .also { ComponentManager.put(it) }
                )
                .build()
                .also { ComponentManager.put(it) }
        }

        fun getSettingsFeature(): SettingsFeatureApi {
            return DaggerSettingsComponent.builder()
                .build()
                .also { ComponentManager.put(it) }
        }
    }
}