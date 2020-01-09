package ru.cherepanovk.feature_events_impl.event.dialog.di

import dagger.Component
import ru.cherepanovk.core.di.dependencies.ContextProvider
import ru.cherepanovk.core_db_api.di.CoreDbApi
import ru.cherepanovk.core_domain_api.di.CoreDomainApi
import ru.cherepanovk.feature_events_impl.event.dialog.DialogDeleteReminderFragment

@Component(
    modules = [DialogDeleteModule::class],
    dependencies = [
        CoreDbApi::class,
        ContextProvider::class,
        CoreDomainApi::class
    ]
)
interface DialogDeleteComponent {
    fun injectDialog(dialogFragment: DialogDeleteReminderFragment)
}