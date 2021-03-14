package ru.cherepanovk.feature_settings_impl.di

import ru.cherepanovk.core.di.BaseFeatureComponentHolder
import ru.cherepanovk.core.di.FeatureComponentContainer
import ru.cherepanovk.core.di.dependencies.ContextProvider
import ru.cherepanovk.core.di.dependencies.GooglePlayServicesAvailabilityProvider
import ru.cherepanovk.core_preferences_api.di.CorePreferencesApi
import ru.cherepanovk.feature_alarm_api.di.FeatureAlarmApi
import ru.cherepanovk.feature_google_calendar_api.di.CoreGoogleCalendarApi
import ru.cherepanovk.feature_settings_api.SettingsFeatureApi

class SettingsComponentHolder(
    featureComponentContainer: FeatureComponentContainer,
) : BaseFeatureComponentHolder<SettingsFeatureApi>(featureComponentContainer) {

    override fun buildFeature(): SettingsFeatureApi {
        return DaggerSettingsComponent.builder()
            .contextProvider(getDependency(ContextProvider::class.java))
            .coreGoogleCalendarApi(getDependency(CoreGoogleCalendarApi::class.java))
            .featureAlarmApi(getDependency(FeatureAlarmApi::class.java))
            .googlePlayServicesAvailabilityProvider(getDependency(GooglePlayServicesAvailabilityProvider::class.java))
            .corePreferencesApi(getDependency(CorePreferencesApi::class.java))
            .build()
    }
}