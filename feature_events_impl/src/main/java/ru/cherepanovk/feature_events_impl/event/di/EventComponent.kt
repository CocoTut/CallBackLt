package ru.cherepanovk.feature_events_impl.event.di

import dagger.Component
import ru.cherepanovk.core.di.dependencies.ContextProvider
import ru.cherepanovk.core.di.dependencies.RootViewProvider
import ru.cherepanovk.core_db_api.di.CoreDbApi
import ru.cherepanovk.core_preferences_api.di.CorePreferencesApi
import ru.cherepanovk.feature_alarm_api.di.CoreDomainApi
import ru.cherepanovk.feature_events_impl.event.EventFragment
import ru.cherepanovk.feature_events_impl.event.dialog.DialogDeleteReminderFragment
import ru.cherepanovk.feature_google_calendar_api.data.GoogleCalendarApi
import ru.cherepanovk.feature_google_calendar_api.di.CoreGoogleCalendarApi

@Component(
    modules = [EventModule::class],
    dependencies = [
        CoreDbApi::class,
        ContextProvider::class,
        CoreDomainApi::class,
        CoreGoogleCalendarApi::class,
        CorePreferencesApi::class,
        RootViewProvider::class
    ]
)
interface EventComponent {
    fun inject(eventFragment: EventFragment)
    fun injectDialog(dialogFragment: DialogDeleteReminderFragment)
}