package ru.cherepanovk.core_preferences_impl.di

import dagger.Module
import dagger.Provides
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import ru.cherepanovk.core.di.FeatureComponentContainer
import ru.cherepanovk.core.di.FeatureComponentHolder
import ru.cherepanovk.core_preferences_api.di.CorePreferencesApi
import javax.inject.Singleton

@Module
class PreferencesFeatureHolderModule {
    @Provides
    @Singleton
    @IntoMap
    @ClassKey(CorePreferencesApi::class)
    fun bindPreferencesFeatureHolder(featureComponentContainer: FeatureComponentContainer): FeatureComponentHolder<*> {
        return PreferencesFeatureComponentHolder(featureComponentContainer)
    }
}