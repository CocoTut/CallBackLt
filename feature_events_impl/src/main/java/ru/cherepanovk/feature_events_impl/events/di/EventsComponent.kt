package ru.cherepanovk.feature_events_impl.events.di

import dagger.Component
import ru.cherepanovk.core.di.dependencies.ContextProvider
import ru.cherepanovk.core.di.viewmodel.ViewModelModule
import ru.cherepanovk.core_db_api.di.CoreDbApi
import ru.cherepanovk.core_preferences_api.di.CorePreferencesApi
import ru.cherepanovk.feature_alarm_api.di.FeatureAlarmApi
import ru.cherepanovk.feature_events_api.EventsFeatureApi
import ru.cherepanovk.feature_events_impl.events.EventsFragment
import ru.cherepanovk.feature_google_calendar_api.di.CoreGoogleCalendarApi

@Component(modules = [
    EventsModule::class,
    ViewModelModule::class
],
    dependencies = [
        ContextProvider::class,
        CoreDbApi::class,
        FeatureAlarmApi::class,
        CorePreferencesApi::class,
        CoreGoogleCalendarApi::class
    ]
    )
interface EventsComponent : EventsFeatureApi {
    fun inject(eventsFragment: EventsFragment)
}