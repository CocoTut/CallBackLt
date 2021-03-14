package ru.cherepanovk.feature_alarm_impl.di

import dagger.Module
import dagger.Provides
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import ru.cherepanovk.core.di.FeatureComponentContainer
import ru.cherepanovk.core.di.FeatureComponentHolder
import ru.cherepanovk.feature_alarm_api.di.FeatureAlarmApi
import javax.inject.Singleton

@Module
class FeatureAlarmApiHolderModule {
    @Singleton
    @Provides
    @IntoMap
    @ClassKey(FeatureAlarmApi::class)
    fun provideFeatureAlarmApi(
        featureComponentContainer: FeatureComponentContainer
    ): FeatureComponentHolder<*> {
        return FeatureAlarmApiHolder(featureComponentContainer)
    }
}