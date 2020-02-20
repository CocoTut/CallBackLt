package ru.cherepanovk.feature_events_impl.events.di

import dagger.Component
import ru.cherepanovk.core.di.dependencies.ContextProvider
import ru.cherepanovk.core.di.dependencies.RootViewProvider
import ru.cherepanovk.core.di.viewmodel.ViewModelModule
import ru.cherepanovk.core_db_api.di.CoreDbApi
import ru.cherepanovk.feature_alarm_api.di.CoreDomainApi
import ru.cherepanovk.feature_events_api.EventsFeatureApi
import ru.cherepanovk.feature_events_impl.events.EventsFragment

@Component(modules = [
    EventsModule::class,
    ViewModelModule::class
],
    dependencies = [
        ContextProvider::class,
        CoreDbApi::class,
        CoreDomainApi::class,
        RootViewProvider::class
    ]
    )
interface EventsComponent : EventsFeatureApi {
    fun inject(eventsFragment: EventsFragment)
}