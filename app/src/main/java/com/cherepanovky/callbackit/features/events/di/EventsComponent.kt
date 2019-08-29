package com.cherepanovky.callbackit.features.events.di

import com.cherepanovky.callbackit.core.di.viewmodel.ViewModelProvider
import com.cherepanovky.callbackit.features.events.EventsFragment
import dagger.Component
import ru.cherepanovk.core.di.dependencies.ContextProvider
import ru.cherepanovk.core_db_api.di.CoreDbApi

@Component(modules = [
    EventsModule::class
],
    dependencies = [
        ContextProvider::class,
        CoreDbApi::class,
        ViewModelProvider::class
    ])
interface EventsComponent {
    fun inject(eventsFragment: EventsFragment)

}