package ru.cherepanovk.feature_events_impl.event.di

import dagger.Binds
import dagger.Module
import ru.cherepanovk.feature_events_impl.event.data.EventRepository
import ru.cherepanovk.feature_events_impl.event.data.EventRepositoryImpl

@Module
abstract class EventModule {

    @Binds
    abstract fun bindRepository(eventRepositoryImpl: EventRepositoryImpl): EventRepository

}