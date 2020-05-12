package ru.cherepanovk.feature_google_calendar_impl.dialog.di

import dagger.Component
import ru.cherepanovk.core.di.dependencies.ContextProvider
import ru.cherepanovk.core.di.dependencies.RootViewProvider
import ru.cherepanovk.core.di.viewmodel.ViewModelModule
import ru.cherepanovk.core_preferences_api.di.CorePreferencesApi
import ru.cherepanovk.feature_google_calendar_api.di.CoreGoogleCalendarApi
import ru.cherepanovk.feature_google_calendar_impl.dialog.AddGoogleAccountDialog

@Component(
    modules = [
        AddGoogleAccountDialogModule::class,
        ViewModelModule::class
    ],
    dependencies = [
        ContextProvider::class,
        CoreGoogleCalendarApi::class,
        CorePreferencesApi::class,
        RootViewProvider::class
    ]
)
interface AddGoogleAccountDialogComponent {
    fun inject(addGoogleAccountDialog: AddGoogleAccountDialog)
}