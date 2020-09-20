package ru.cherepanovk.feature_google_calendar_impl.di

import dagger.Component
import ru.cherepanovk.core.di.dependencies.ContextProvider
import ru.cherepanovk.core.di.dependencies.RootViewProvider
import ru.cherepanovk.core_db_api.di.CoreDbApi
import ru.cherepanovk.core_preferences_api.di.CorePreferencesApi
import ru.cherepanovk.feature_alarm_api.di.FeatureAlarmApi
import ru.cherepanovk.feature_google_calendar_api.di.CoreGoogleCalendarApi
import ru.cherepanovk.feature_google_calendar_impl.addaccount.di.AddGoogleAccountDialogComponent
import ru.cherepanovk.feature_google_calendar_impl.loadevents.di.LoadEventsComponent

@Component(
    modules = [GoogleCalendarApiModule::class],
    dependencies = [
        ContextProvider::class,
        CorePreferencesApi::class,
        RootViewProvider::class,
        CoreDbApi::class,
        FeatureAlarmApi::class
    ]
)

interface GoogleCalendarApiComponent  : CoreGoogleCalendarApi {
    fun getAddGoogleAccountDialogComponent(): AddGoogleAccountDialogComponent
    fun getLoadEventsComponent():LoadEventsComponent
}