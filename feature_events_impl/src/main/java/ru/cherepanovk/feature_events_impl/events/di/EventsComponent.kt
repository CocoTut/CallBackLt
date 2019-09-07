package ru.cherepanovk.feature_events_impl.events.di

import dagger.Component
import ru.cherepanovk.core.di.dependencies.ContextProvider
import ru.cherepanovk.core_db_api.di.CoreDbApi
import ru.cherepanovk.feature_events_api.EventsFeatureApi
import ru.cherepanovk.feature_events_impl.events.EventsFragment

@Component(modules = [
    EventsModule::class
],
    dependencies = [
        ContextProvider::class,
        CoreDbApi::class
    ])
interface EventsComponent : EventsFeatureApi {
    fun inject(eventsFragment: EventsFragment)

}