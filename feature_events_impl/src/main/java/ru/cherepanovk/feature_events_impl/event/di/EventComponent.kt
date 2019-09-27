package ru.cherepanovk.feature_events_impl.event.di

import androidx.fragment.app.DialogFragment
import dagger.Component
import ru.cherepanovk.core.di.dependencies.ContextProvider
import ru.cherepanovk.core_db_api.data.DbApi
import ru.cherepanovk.core_db_api.di.CoreDbApi
import ru.cherepanovk.feature_events_impl.event.EventFragment
import ru.cherepanovk.feature_events_impl.event.dialog.DialogDeleteReminderFragment

@Component(
    modules = [EventModule::class],
    dependencies = [
        CoreDbApi::class,
        ContextProvider::class
    ]
)
interface EventComponent {
    fun inject(eventFragment: EventFragment)
    fun injectDialog(dialogFragment: DialogDeleteReminderFragment)
}