package ru.cherepanovk.core_db_impl.di

import ru.cherepanovk.core.di.BaseFeatureComponentHolder
import ru.cherepanovk.core.di.FeatureComponentContainer
import ru.cherepanovk.core.di.dependencies.ContextProvider
import ru.cherepanovk.core_db_api.di.CoreDbApi

class CoreDbApiHolder(
    featureComponentContainer: FeatureComponentContainer
) : BaseFeatureComponentHolder<CoreDbApi>(featureComponentContainer)  {
    override fun buildFeature(): CoreDbApi {
      return DaggerCoreDbComponent.builder()
            .contextProvider(getDependency(ContextProvider::class.java))
            .build()
    }
}