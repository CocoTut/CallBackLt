package ru.cherepanovk.feature_events_impl.events.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.cherepanovk.core.di.dependencies.FeatureNavigator
import ru.cherepanovk.core.di.viewmodel.ViewModelKey
import ru.cherepanovk.core.platform.FeatureNavigatorImpl
import ru.cherepanovk.feature_events_api.EventsFeatureStarter
import ru.cherepanovk.feature_events_impl.EventsFeatureStarterImpl
import ru.cherepanovk.feature_events_impl.events.EventsViewModel
import ru.cherepanovk.feature_events_impl.events.data.EventsRepository
import ru.cherepanovk.feature_events_impl.events.data.EventsRepositoryImpl
import ru.cherepanovk.feature_events_impl.events.data.GoogleAccountRepository
import ru.cherepanovk.feature_events_impl.events.data.GoogleAccountRepositoryImpl

@Module
abstract class EventsModule {

    @Binds
    @IntoMap
    @ViewModelKey(EventsViewModel::class)
    abstract fun binViewModel(eventsViewModel: EventsViewModel): ViewModel

    @Binds
    abstract fun bindRepository(eventsRepositoryImpl: EventsRepositoryImpl): EventsRepository

    @Binds
    abstract fun bindEventsStarter(eventsFeatureStarterImpl: EventsFeatureStarterImpl): EventsFeatureStarter

    @Binds
    abstract fun bindFeatureNavigator(featureNavigatorImpl: FeatureNavigatorImpl): FeatureNavigator

    @Binds
    abstract fun bindGoogleAccountRepository(googleAccountRepository: GoogleAccountRepositoryImpl): GoogleAccountRepository
}
