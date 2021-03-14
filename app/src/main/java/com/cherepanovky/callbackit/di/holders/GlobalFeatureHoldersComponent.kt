package com.cherepanovky.callbackit.di.holders

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import ru.cherepanovk.core.di.FeatureComponentContainer
import ru.cherepanovk.core.di.FeatureComponentHolder
import javax.inject.Singleton

@Singleton
@Component(modules = [GlobalFeatureHoldersModule::class])
interface GlobalFeatureHoldersComponent {

    fun getFeatureHolders(): Map<Class<*>, FeatureComponentHolder<*>>

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun featureContainerManager(featureContainer: FeatureComponentContainer): Builder

        fun build(): GlobalFeatureHoldersComponent
    }
}