package com.cherepanovky.callbackit.features.events.di

import com.cherepanovky.callbackit.core.di.ViewComponentBuilder
import com.cherepanovky.callbackit.features.events.EventsFragment
import dagger.Subcomponent

@Subcomponent(modules = [EventsModule::class])
interface EventsComponent {
    fun inject(eventsFragment: EventsFragment)

    @Subcomponent.Builder
    interface Builder : ViewComponentBuilder<EventsComponent>

}