package ru.cherepanovk.feature_google_calendar_impl.addaccount.di

import dagger.Component
import dagger.Subcomponent
import ru.cherepanovk.core.di.dependencies.ContextProvider
import ru.cherepanovk.core.di.dependencies.RootViewProvider
import ru.cherepanovk.core.di.viewmodel.ViewModelModule
import ru.cherepanovk.core_preferences_api.di.CorePreferencesApi
import ru.cherepanovk.feature_google_calendar_api.di.CoreGoogleCalendarApi
import ru.cherepanovk.feature_google_calendar_impl.addaccount.AddGoogleAccountDialog

@Subcomponent(
    modules = [
        AddGoogleAccountDialogModule::class,
        ViewModelModule::class
    ]
)
interface AddGoogleAccountDialogComponent {
    fun inject(addGoogleAccountDialog: AddGoogleAccountDialog)
}