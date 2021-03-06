package ru.cherepanovk.feature_settings_impl.di

import dagger.Component
import ru.cherepanovk.core.di.dependencies.ContextProvider
import ru.cherepanovk.core.di.dependencies.GooglePlayServicesAvailabilityProvider
import ru.cherepanovk.core.di.viewmodel.ViewModelModule
import ru.cherepanovk.core_preferences_api.di.CorePreferencesApi
import ru.cherepanovk.feature_alarm_api.di.FeatureAlarmApi
import ru.cherepanovk.feature_google_calendar_api.di.CoreGoogleCalendarApi
import ru.cherepanovk.feature_settings_api.SettingsFeatureApi
import ru.cherepanovk.feature_settings_impl.SettingsFragment

@Component(
    modules = [
        SettingsModule::class,
        ViewModelModule::class
    ],
    dependencies = [
        ContextProvider::class,
        CorePreferencesApi::class,
        CoreGoogleCalendarApi::class,
        FeatureAlarmApi::class,
        GooglePlayServicesAvailabilityProvider::class
    ]
)
interface SettingsComponent : SettingsFeatureApi {
    fun inject(settingsFragment: SettingsFragment)
}