package ru.cherepanovk.feature_events_impl.dialog.reschedule.di

import dagger.Subcomponent
import ru.cherepanovk.feature_events_impl.dialog.reschedule.DialogRescheduleFragment

@Subcomponent(
    modules = [RescheduleModule::class],
    )
interface RescheduleComponent {
    fun inject(dialogRescheduleFragment: DialogRescheduleFragment)

}