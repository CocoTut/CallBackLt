package ru.cherepanovk.feature_google_calendar_impl.addaccount.di

import dagger.Subcomponent
import ru.cherepanovk.core.di.viewmodel.ViewModelModule
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