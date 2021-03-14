package ru.cherepanovk.core.di

import java.util.*

class FeatureComponentContainerManager :  FeatureComponentContainer{

    private val mGlobalFeatureHolders: MutableMap<Class<*>, FeatureComponentHolder<*>> = HashMap()


    override fun <T> getFeature(key: Class<T>?): T {
        return getFeatureHolder(key!!).getFeatureComponent()
    }

    override fun <T> getDependency(key: Class<T>?): T {
        return getFeatureHolder(key!!).getFeatureComponent()
    }


    fun <T> getFeatureHolder(key: Class<T>): FeatureComponentHolder<T> {
        return retrieveFeatureHolder(key, )
    }

    fun initialize(globalFeaturesInitializer: FeaturesInitializer) {
        mGlobalFeatureHolders.putAll(globalFeaturesInitializer.initialize())
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> retrieveFeatureHolder(key: Class<T>): FeatureComponentHolder<T> {
        return mGlobalFeatureHolders[key] as FeatureComponentHolder<T>
    }

}