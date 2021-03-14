package ru.cherepanovk.core_db_impl.di

import dagger.Module
import dagger.Provides
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import ru.cherepanovk.core.di.FeatureComponentContainer
import ru.cherepanovk.core.di.FeatureComponentHolder
import ru.cherepanovk.core_db_api.di.CoreDbApi
import javax.inject.Singleton

@Module
class CoreDbApiHolderModule {
    @Singleton
    @Provides
    @IntoMap
    @ClassKey(CoreDbApi::class)
    fun provideCoreDbApiHolder(featureComponentContainer: FeatureComponentContainer): FeatureComponentHolder<*> {
        return CoreDbApiHolder(featureComponentContainer)
    }
}