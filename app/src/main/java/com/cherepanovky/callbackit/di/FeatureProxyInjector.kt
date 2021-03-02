package com.cherepanovky.callbackit.di

import ru.cherepanovk.core.di.ComponentManager
import ru.cherepanovk.core.di.getOrThrow
import ru.cherepanovk.core_db_api.di.CoreDbApi
import ru.cherepanovk.core_db_impl.di.DaggerCoreDbComponent
import ru.cherepanovk.core_preferences_impl.di.DaggerCorePreferencesComponent
import ru.cherepanovk.feature_alarm_api.di.FeatureAlarmApi

import ru.cherepanovk.feature_alarm_impl.di.DaggerFeatureAlarmComponent
import ru.cherepanovk.feature_alarm_impl.di.FeatureAlarmComponent
import ru.cherepanovk.feature_events_api.EventsFeatureApi
import ru.cherepanovk.feature_events_impl.events.di.DaggerEventsComponent
import ru.cherepanovk.feature_events_impl.events.di.EventsComponent
import ru.cherepanovk.feature_google_calendar_impl.di.DaggerGoogleCalendarApiComponent
import ru.cherepanovk.feature_google_calendar_impl.di.GoogleCalendarApiComponent
import ru.cherepanovk.feature_settings_api.SettingsFeatureApi
import ru.cherepanovk.feature_settings_impl.di.DaggerSettingsComponent


class FeatureProxyInjector {
    companion object {
        fun getEventsFeature(): EventsFeatureApi {
            return ComponentManager.get(EventsComponent::class) ?: getEventsComponent()
        }

        fun getEventsFeatureComponent(): EventsFeatureApi {
            return ComponentManager.get(EventsComponent::class) ?: getEventsComponent()
        }

        fun getSettingsFeature(): SettingsFeatureApi {
            return DaggerSettingsComponent.builder()
                .corePreferencesApi(
                    ComponentManager.getOrThrow()
                )
                .contextProvider(ComponentManager.getOrThrow())
                .rootViewProvider(ComponentManager.getOrThrow())
                .coreGoogleCalendarApi(
                    ComponentManager.getOrThrow()
                )
                .featureAlarmApi(
                    ComponentManager.get(FeatureAlarmComponent::class) ?: getFeatureAlarmApi()
                )
                .build()
                .also { ComponentManager.put(it) }
        }

        private fun getEventsComponent(): EventsComponent {
            return DaggerEventsComponent.builder()
                .contextProvider(ComponentManager.getOrThrow())
                .rootViewProvider(ComponentManager.getOrThrow())
                .coreDbApi(
                    ComponentManager.getOrThrow()
                )
                .featureAlarmApi(
                    ComponentManager.get(FeatureAlarmComponent::class) ?: getFeatureAlarmApi()
                )
                .corePreferencesApi(
                    ComponentManager.getOrThrow()
                )
                .coreGoogleCalendarApi(
                    getGoogleCalendarApi()
                )
                .build()
                .also { ComponentManager.put(it) }
        }

        private fun getGoogleCalendarApi() =
            ComponentManager.get(GoogleCalendarApiComponent::class)
                ?: DaggerGoogleCalendarApiComponent.builder()
                    .contextProvider(ComponentManager.getOrThrow())
                    .corePreferencesApi(ComponentManager.getOrThrow())
                    .rootViewProvider(ComponentManager.getOrThrow())
                    .coreDbApi(ComponentManager.getOrThrow())
                    .featureAlarmApi(ComponentManager.getOrThrow())
                    .build()
                    .also { ComponentManager.put(it) }

        private fun getFeatureAlarmApi(): FeatureAlarmComponent  =
            DaggerFeatureAlarmComponent.builder()
                .contextProvider(ComponentManager.getOrThrow())
                .build()
                .also { ComponentManager.put(it) }
    }
}