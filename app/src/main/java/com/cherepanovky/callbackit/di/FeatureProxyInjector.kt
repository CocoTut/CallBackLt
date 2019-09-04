package com.cherepanovky.callbackit.di

import ru.cherepanovk.core.di.ComponentManager
import ru.cherepanovk.core.di.getOrThrow
import ru.cherepanovk.core_db_impl.di.DaggerCoreDbComponent
import ru.cherepanovk.feature_events_api.EventsFeatureApi
import ru.cherepanovk.feature_events_impl.events.di.DaggerEventsComponent


class FeatureProxyInjector {
    companion object {
        fun getEventsFeature(): EventsFeatureApi{
            return DaggerEventsComponent.builder()
                .contextProvider(ComponentManager.getOrThrow())
                .coreDbApi(
                    DaggerCoreDbComponent.builder()
                        .contextProvider(ComponentManager.getOrThrow())
                        .build()
                )
                .build()
                .also { ComponentManager.put(it) }
        }
    }
}