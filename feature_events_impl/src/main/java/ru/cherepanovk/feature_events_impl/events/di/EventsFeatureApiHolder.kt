package ru.cherepanovk.feature_events_impl.events.di

import ru.cherepanovk.core.di.BaseFeatureComponentHolder
import ru.cherepanovk.core.di.FeatureComponentContainer
import ru.cherepanovk.core.di.dependencies.ContextProvider
import ru.cherepanovk.core.di.dependencies.GooglePlayServicesAvailabilityProvider
import ru.cherepanovk.core_db_api.di.CoreDbApi
import ru.cherepanovk.core_preferences_api.di.CorePreferencesApi
import ru.cherepanovk.feature_alarm_api.di.FeatureAlarmApi
import ru.cherepanovk.feature_events_api.EventsFeatureApi
import ru.cherepanovk.feature_google_calendar_api.di.CoreGoogleCalendarApi

class EventsFeatureApiHolder(
    featureComponentContainer: FeatureComponentContainer
) : BaseFeatureComponentHolder<EventsFeatureApi>(featureComponentContainer) {
    override fun buildFeature(): EventsFeatureApi {
        return DaggerEventsComponent.builder()
            .featureAlarmApi(getDependency(FeatureAlarmApi::class.java))
            .contextProvider(getDependency(ContextProvider::class.java))
            .coreDbApi(getDependency(CoreDbApi::class.java))
            .featureAlarmApi(getDependency(FeatureAlarmApi::class.java))
            .googlePlayServicesAvailabilityProvider(
                getDependency(GooglePlayServicesAvailabilityProvider::class.java)
            )
            .corePreferencesApi(getDependency(CorePreferencesApi::class.java))
            .coreGoogleCalendarApi(getDependency(CoreGoogleCalendarApi::class.java))
            .build()
    }

}