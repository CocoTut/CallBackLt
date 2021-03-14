package ru.cherepanovk.feature_google_calendar_impl.di

import ru.cherepanovk.core.di.BaseFeatureComponentHolder
import ru.cherepanovk.core.di.FeatureComponentContainer
import ru.cherepanovk.core.di.dependencies.ContextProvider
import ru.cherepanovk.core_db_api.di.CoreDbApi
import ru.cherepanovk.core_preferences_api.di.CorePreferencesApi
import ru.cherepanovk.feature_alarm_api.di.FeatureAlarmApi
import ru.cherepanovk.feature_google_calendar_api.di.CoreGoogleCalendarApi

class GoogleCalendarApiHolder(
    featureComponentContainer: FeatureComponentContainer
) : BaseFeatureComponentHolder<CoreGoogleCalendarApi>(featureComponentContainer) {
    override fun buildFeature(): CoreGoogleCalendarApi {
        return DaggerGoogleCalendarApiComponent.builder()
            .contextProvider(getDependency(ContextProvider::class.java))
            .corePreferencesApi(getDependency(CorePreferencesApi::class.java))
            .coreDbApi(getDependency(CoreDbApi::class.java))
            .featureAlarmApi(getDependency(FeatureAlarmApi::class.java))
            .build()
    }
}