package ru.cherepanovk.feature_events_impl.dialog.delete.di

import dagger.Subcomponent
import ru.cherepanovk.feature_events_impl.dialog.delete.DialogDeleteReminderFragment

@Subcomponent(modules = [DialogDeleteModule::class])
interface DialogDeleteComponent {
    fun injectDialog(dialogFragment: DialogDeleteReminderFragment)
}