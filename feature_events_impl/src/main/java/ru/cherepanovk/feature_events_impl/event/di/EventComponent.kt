package ru.cherepanovk.feature_events_impl.event.di

import dagger.Subcomponent
import ru.cherepanovk.feature_events_impl.event.EventFragment

@Subcomponent(modules = [EventModule::class])
interface EventComponent {
    fun inject(eventFragment: EventFragment)
}