package ru.cherepanovk.feature_events_impl.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.cherepanovk.core.di.viewmodel.ViewModelKey
import ru.cherepanovk.feature_events_impl.EventsViewModel
import ru.cherepanovk.feature_events_impl.repository.EventsRepository
import ru.cherepanovk.feature_events_impl.repository.EventsRepositoryImpl

@Module
abstract class EventsModule {

    @Binds
    @IntoMap
    @ViewModelKey(EventsViewModel::class)
    abstract fun binViewModel(eventsViewModel: EventsViewModel): ViewModel

    @Binds
    abstract fun bindRepository(eventsRepositoryImpl: EventsRepositoryImpl): EventsRepository
}