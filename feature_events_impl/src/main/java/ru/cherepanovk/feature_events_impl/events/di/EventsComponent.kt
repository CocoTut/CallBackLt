package ru.cherepanovk.feature_events_impl.events.di

import dagger.Component
import ru.cherepanovk.core.di.dependencies.ContextProvider
import ru.cherepanovk.core.di.dependencies.GooglePlayServicesAvailabilityProvider
import ru.cherepanovk.core.di.viewmodel.ViewModelModule
import ru.cherepanovk.core_db_api.di.CoreDbApi
import ru.cherepanovk.core_preferences_api.di.CorePreferencesApi
import ru.cherepanovk.feature_alarm_api.di.FeatureAlarmApi
import ru.cherepanovk.feature_events_api.EventsFeatureApi
import ru.cherepanovk.feature_events_impl.dialog.delete.di.DialogDeleteComponent
import ru.cherepanovk.feature_events_impl.dialog.reschedule.di.RescheduleComponent
import ru.cherepanovk.feature_events_impl.event.di.EventComponent
import ru.cherepanovk.feature_events_impl.events.EventsFragment
import ru.cherepanovk.feature_google_calendar_api.di.CoreGoogleCalendarApi

@Component(modules = [
    EventsModule::class,
    ViewModelModule::class
],
    dependencies = [
        FeatureAlarmApi::class,
        ContextProvider::class,
        CoreDbApi::class,
        FeatureAlarmApi::class,
        GooglePlayServicesAvailabilityProvider::class,
        CorePreferencesApi::class,
        CoreGoogleCalendarApi::class
    ]
    )
interface EventsComponent : EventsFeatureApi {
    fun inject(eventsFragment: EventsFragment)

    fun getEventComponent(): EventComponent
    fun getRescheduleComponent(): RescheduleComponent
    fun getDialogDeleteComponent(): DialogDeleteComponent
}