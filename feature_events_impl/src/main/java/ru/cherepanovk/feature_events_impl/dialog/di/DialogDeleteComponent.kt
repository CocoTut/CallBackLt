package ru.cherepanovk.feature_events_impl.dialog.di

import dagger.Component
import ru.cherepanovk.core.di.dependencies.ContextProvider
import ru.cherepanovk.core.di.dependencies.RootViewProvider
import ru.cherepanovk.core_db_api.di.CoreDbApi
import ru.cherepanovk.core_preferences_api.di.CorePreferencesApi
import ru.cherepanovk.feature_alarm_api.di.FeatureAlarmApi
import ru.cherepanovk.feature_events_impl.dialog.DialogDeleteReminderFragment
import ru.cherepanovk.feature_google_calendar_api.di.CoreGoogleCalendarApi

@Component(
    modules = [DialogDeleteModule::class],
    dependencies = [
        CoreDbApi::class,
        ContextProvider::class,
        FeatureAlarmApi::class,
        CoreGoogleCalendarApi::class,
        CorePreferencesApi::class,
        RootViewProvider::class
    ]
)
interface DialogDeleteComponent {
    fun injectDialog(dialogFragment: DialogDeleteReminderFragment)
}