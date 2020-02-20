package ru.cherepanovk.feature_events_impl.event.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.cherepanovk.core.di.viewmodel.ViewModelKey
import ru.cherepanovk.core.utils.Mapper
import ru.cherepanovk.core_db_api.data.Reminder
import ru.cherepanovk.feature_events_impl.event.EventViewModel
import ru.cherepanovk.feature_events_impl.event.ReminderView
import ru.cherepanovk.feature_events_impl.event.ReminderViewMapper
import ru.cherepanovk.feature_events_impl.event.data.EventRepository
import ru.cherepanovk.feature_events_impl.event.data.EventRepositoryImpl
import ru.cherepanovk.feature_events_impl.event.dialog.DeleteReminderViewModel

@Module
abstract class EventModule {
    @Binds
    @IntoMap
    @ViewModelKey(EventViewModel::class)
    abstract fun bindViewModel(eventViewModel: EventViewModel): ViewModel

    @Binds
    abstract fun bindRepository(eventRepositoryImpl: EventRepositoryImpl): EventRepository

}