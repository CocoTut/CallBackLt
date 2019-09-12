package ru.cherepanovk.feature_events_impl.events.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import ru.cherepanovk.core.di.viewmodel.ViewModelKey
import ru.cherepanovk.core.utils.Mapper
import ru.cherepanovk.core_db_api.data.Reminder
import ru.cherepanovk.feature_events_api.EventsFeatureStarter
import ru.cherepanovk.feature_events_impl.EventsFeatureStarterImpl
import ru.cherepanovk.feature_events_impl.events.EventsViewModel
import ru.cherepanovk.feature_events_impl.events.ItemReminder
import ru.cherepanovk.feature_events_impl.events.ItemReminderMapper
import ru.cherepanovk.feature_events_impl.events.data.EventsRepository
import ru.cherepanovk.feature_events_impl.events.data.EventsRepositoryImpl

@Module
abstract class EventsModule {

    @Binds
    @IntoMap
    @ViewModelKey(EventsViewModel::class)
    abstract fun binViewModel(eventsViewModel: EventsViewModel): ViewModel

    @Binds
    abstract fun bindRepository(eventsRepositoryImpl: EventsRepositoryImpl): EventsRepository

    @Binds
    abstract fun bindLoginStarter(eventsFeatureStarterImpl: EventsFeatureStarterImpl): EventsFeatureStarter

//    @Binds
//    abstract fun bindItemMapper(itemReminderMapper: ItemReminderMapper): Mapper<Reminder, ItemReminder>


}

//@Module
//object MapperModule {
//    @JvmStatic
//    @Provides
//    fun provideItemMapper(): Mapper<Reminder, ItemReminder> {
//        return ItemReminderMapper()
//    }
//
//}