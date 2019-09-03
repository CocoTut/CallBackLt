package com.cherepanovky.callbackit.features.events.di

import androidx.lifecycle.ViewModel
import com.cherepanovky.callbackit.features.events.EventsViewModel
import com.cherepanovky.callbackit.features.events.repository.EventsRepository
import com.cherepanovky.callbackit.features.events.repository.EventsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.cherepanovk.core.di.viewmodel.ViewModelKey

@Module
abstract class EventsModule {

    @Binds
    @IntoMap
    @ViewModelKey(EventsViewModel::class)
    abstract fun binViewModel(eventsViewModel: EventsViewModel): ViewModel

    @Binds
    abstract fun bindRepository(eventsRepositoryImpl: EventsRepositoryImpl): EventsRepository
}