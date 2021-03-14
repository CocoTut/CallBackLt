package ru.cherepanovk.feature_google_calendar_impl.di

import dagger.Module
import dagger.Provides
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import ru.cherepanovk.core.di.FeatureComponentContainer
import ru.cherepanovk.core.di.FeatureComponentHolder
import ru.cherepanovk.feature_google_calendar_api.di.CoreGoogleCalendarApi
import javax.inject.Singleton

@Module
class GoogleCalendarApiHolderModule {

    @Singleton
    @Provides
    @IntoMap
    @ClassKey(CoreGoogleCalendarApi::class)
    fun provideCoreGoogleCalendarApi(
        featureComponentContainer: FeatureComponentContainer
    ): FeatureComponentHolder<*> {
        return GoogleCalendarApiHolder(featureComponentContainer)
    }
}