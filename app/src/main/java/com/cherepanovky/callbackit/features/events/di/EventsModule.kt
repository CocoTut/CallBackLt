package com.cherepanovky.callbackit.features.events.di

import androidx.lifecycle.ViewModel
import com.cherepanovky.callbackit.core.di.viewmodel.ViewModelKey
import com.cherepanovky.callbackit.features.events.EventsViewModel
import com.cherepanovky.callbackit.features.events.repository.EventsRepository
import com.cherepanovky.callbackit.features.events.repository.EventsRepositroyImpl
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class EventsModule {

    @Binds
    @IntoMap
    @ViewModelKey(EventsViewModel::class)
    abstract fun binViewModel(eventsViewModel: EventsViewModel): ViewModel

    @Binds
    abstract fun bindRepository(eventsRepositroyImpl: EventsRepositroyImpl): EventsRepository
}