package ru.cherepanovk.feature_google_calendar_impl.dialog.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.cherepanovk.core.di.viewmodel.ViewModelKey
import ru.cherepanovk.feature_google_calendar_impl.dialog.AddGoogleAccountViewModel

@Module
abstract class AddGoogleAccountDialogModule {
    @Binds
    @IntoMap
    @ViewModelKey(AddGoogleAccountViewModel::class)
    abstract fun bindViewModel(addGoogleAccountViewModel: AddGoogleAccountViewModel): ViewModel

}