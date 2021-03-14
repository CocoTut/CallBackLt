package ru.cherepanovk.feature_settings_impl.di

import dagger.Module
import dagger.Provides
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import ru.cherepanovk.core.di.FeatureComponentContainer
import ru.cherepanovk.core.di.FeatureComponentHolder
import ru.cherepanovk.feature_settings_api.SettingsFeatureApi
import javax.inject.Singleton

@Module
class SettingsHolderModule {

    @Singleton
    @Provides
    @IntoMap
    @ClassKey(SettingsFeatureApi::class)
    fun provideSettingsHolder(featureComponentContainer: FeatureComponentContainer): FeatureComponentHolder<*> {
        return SettingsComponentHolder(featureComponentContainer)
    }
}