package ru.cherepanovk.feature_events_impl.di

import dagger.Component
import ru.cherepanovk.core.di.dependencies.ContextProvider
import ru.cherepanovk.core_db_api.di.CoreDbApi
import ru.cherepanovk.feature_events_impl.EventsFragment

@Component(modules = [
    EventsModule::class
],
    dependencies = [
        ContextProvider::class,
        CoreDbApi::class
    ])
interface EventsComponent {
    fun inject(eventsFragment: EventsFragment)

}