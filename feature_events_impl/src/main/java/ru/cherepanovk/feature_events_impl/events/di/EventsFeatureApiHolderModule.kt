package ru.cherepanovk.feature_events_impl.events.di

import dagger.Module
import dagger.Provides
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import ru.cherepanovk.core.di.FeatureComponentContainer
import ru.cherepanovk.core.di.FeatureComponentHolder
import ru.cherepanovk.feature_events_api.EventsFeatureApi
import javax.inject.Singleton

@Module
class EventsFeatureApiHolderModule {
    @Singleton
    @Provides
    @IntoMap
    @ClassKey(EventsFeatureApi::class)
    fun provideEventsFeatureApiHolder(featureComponentContainer: FeatureComponentContainer): FeatureComponentHolder<*> {
        return EventsFeatureApiHolder(featureComponentContainer)
    }
}