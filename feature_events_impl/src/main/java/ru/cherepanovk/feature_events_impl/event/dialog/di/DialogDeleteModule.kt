package ru.cherepanovk.feature_events_impl.event.dialog.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.cherepanovk.core.di.viewmodel.ViewModelKey
import ru.cherepanovk.feature_events_impl.event.data.EventRepository
import ru.cherepanovk.feature_events_impl.event.data.EventRepositoryImpl
import ru.cherepanovk.feature_events_impl.event.dialog.DeleteReminderViewModel

@Module
abstract class DialogDeleteModule {
    @Binds
    @IntoMap
    @ViewModelKey(DeleteReminderViewModel::class)
    abstract fun bindDialogViewModel(deleteReminderViewModel: DeleteReminderViewModel): ViewModel

    @Binds
    abstract fun bindRepository(eventRepositoryImpl: EventRepositoryImpl): EventRepository

}