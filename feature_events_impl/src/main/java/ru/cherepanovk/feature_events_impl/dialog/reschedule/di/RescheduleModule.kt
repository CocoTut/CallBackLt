package ru.cherepanovk.feature_events_impl.dialog.reschedule.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.cherepanovk.core.di.viewmodel.ViewModelKey
import ru.cherepanovk.feature_events_impl.dialog.reschedule.DialogRescheduleViewModel
import ru.cherepanovk.feature_events_impl.dialog.reschedule.data.RescheduleRepository
import ru.cherepanovk.feature_events_impl.dialog.reschedule.data.RescheduleRepositoryImpl
import ru.cherepanovk.feature_events_impl.event.data.EventRepository
import ru.cherepanovk.feature_events_impl.event.data.EventRepositoryImpl

@Module
abstract class RescheduleModule {
    @Binds
    @IntoMap
    @ViewModelKey(DialogRescheduleViewModel::class)
    abstract fun bindDialogViewModel(dialogRescheduleViewModel: DialogRescheduleViewModel): ViewModel


    @Binds
    abstract fun bindRescheduleRepository(rescheduleRepositoryImpl: RescheduleRepositoryImpl): RescheduleRepository
}