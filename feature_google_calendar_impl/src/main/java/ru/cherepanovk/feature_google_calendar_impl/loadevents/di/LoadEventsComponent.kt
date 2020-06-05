package ru.cherepanovk.feature_google_calendar_impl.loadevents.di

import dagger.Subcomponent
import ru.cherepanovk.core.di.viewmodel.ViewModelModule
import ru.cherepanovk.feature_google_calendar_impl.loadevents.DialogLoadEvents

@Subcomponent(
    modules = [LoadEventsModule::class, ViewModelModule::class]
)
interface LoadEventsComponent {
    fun inject(dialogLoadEvents: DialogLoadEvents)
}