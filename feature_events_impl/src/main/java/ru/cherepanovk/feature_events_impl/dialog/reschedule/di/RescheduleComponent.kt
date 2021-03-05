package ru.cherepanovk.feature_events_impl.dialog.reschedule.di

import dagger.Component
import ru.cherepanovk.core.di.dependencies.ContextProvider
import ru.cherepanovk.core_db_api.di.CoreDbApi
import ru.cherepanovk.core_preferences_api.di.CorePreferencesApi
import ru.cherepanovk.feature_alarm_api.di.FeatureAlarmApi
import ru.cherepanovk.feature_events_impl.dialog.reschedule.DialogRescheduleFragment
import ru.cherepanovk.feature_google_calendar_api.di.CoreGoogleCalendarApi

@Component(
    modules = [RescheduleModule::class],
    dependencies = [
        CoreDbApi::class,
        ContextProvider::class,
        FeatureAlarmApi::class,
        CoreGoogleCalendarApi::class,
        CorePreferencesApi::class
    ]
    )
interface RescheduleComponent {
    fun inject(dialogRescheduleFragment: DialogRescheduleFragment)
}