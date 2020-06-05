package ru.cherepanovk.feature_google_calendar_impl.loadevents.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.cherepanovk.core.di.viewmodel.ViewModelKey
import ru.cherepanovk.feature_google_calendar_impl.loadevents.LoadEventsViewModel

@Module
abstract class LoadEventsModule {
    @Binds
    @IntoMap
    @ViewModelKey(LoadEventsViewModel::class)
    abstract fun bindViewModel(loadEventsViewModel: LoadEventsViewModel): ViewModel
}