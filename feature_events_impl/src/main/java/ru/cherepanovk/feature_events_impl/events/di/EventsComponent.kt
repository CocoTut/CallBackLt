package ru.cherepanovk.feature_events_impl.events.di

import dagger.Component
import dagger.Subcomponent
import ru.cherepanovk.core.di.dependencies.ContextProvider
import ru.cherepanovk.core.di.dependencies.RootViewProvider
import ru.cherepanovk.core.di.dependencies.ViewComponentBuilder
import ru.cherepanovk.core.platform.RootView
import ru.cherepanovk.core_db_api.di.CoreDbApi
import ru.cherepanovk.core_domain_api.di.CoreDomainApi
import ru.cherepanovk.feature_events_api.EventsFeatureApi
import ru.cherepanovk.feature_events_impl.events.EventsFragment

@Component(modules = [
    EventsModule::class
],
    dependencies = [
        ContextProvider::class,
        CoreDbApi::class,
        CoreDomainApi::class,
        RootViewProvider::class
    ])
interface EventsComponent : EventsFeatureApi {
    fun inject(eventsFragment: EventsFragment)
}