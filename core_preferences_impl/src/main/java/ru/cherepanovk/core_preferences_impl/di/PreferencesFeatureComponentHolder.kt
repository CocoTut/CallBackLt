package ru.cherepanovk.core_preferences_impl.di

import ru.cherepanovk.core.di.BaseFeatureComponentHolder
import ru.cherepanovk.core.di.FeatureComponentContainer
import ru.cherepanovk.core.di.dependencies.ContextProvider
import ru.cherepanovk.core_preferences_api.di.CorePreferencesApi

class PreferencesFeatureComponentHolder(
    featureComponentContainer: FeatureComponentContainer,
) : BaseFeatureComponentHolder<CorePreferencesApi>(featureComponentContainer) {

    override fun buildFeature(): CorePreferencesApi {
       return DaggerCorePreferencesComponent.builder()
            .contextProvider(getDependency(ContextProvider::class.java))
            .build()
    }
}