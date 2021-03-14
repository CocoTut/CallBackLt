package com.cherepanovky.callbackit.di.holders

import android.app.Application
import ru.cherepanovk.core.di.FeatureComponentContainer
import ru.cherepanovk.core.di.FeatureComponentHolder
import ru.cherepanovk.core.di.FeaturesInitializer

class GlobalHoldersFeaturesInitializer(
    private val application: Application,
    private val featureComponentContainer: FeatureComponentContainer
) : FeaturesInitializer {

    override fun initialize(): Map<Class<*>, FeatureComponentHolder<*>> {
        return DaggerGlobalFeatureHoldersComponent.builder()
            .application(application)
            .featureContainerManager(featureComponentContainer)
            .build()
            .getFeatureHolders()
    }
}