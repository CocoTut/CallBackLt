package ru.cherepanovk.feature_events_impl.event.di

import dagger.Component
import dagger.Subcomponent
import ru.cherepanovk.core.di.dependencies.ContextProvider
import ru.cherepanovk.core.di.dependencies.ViewComponentBuilder
import ru.cherepanovk.core_db_api.di.CoreDbApi
import ru.cherepanovk.core_domain_api.di.CoreDomainApi
import ru.cherepanovk.feature_events_impl.event.EventFragment
import ru.cherepanovk.feature_events_impl.event.dialog.DialogDeleteReminderFragment
import ru.cherepanovk.feature_events_impl.events.di.EventsComponent

@Component(
    modules = [EventModule::class],
    dependencies = [
        CoreDbApi::class,
        ContextProvider::class,
        CoreDomainApi::class
    ]
)
interface EventComponent {
    fun inject(eventFragment: EventFragment)
    fun injectDialog(dialogFragment: DialogDeleteReminderFragment)
}