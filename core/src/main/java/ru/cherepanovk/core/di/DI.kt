@file:Suppress("UNCHECKED_CAST")

package ru.cherepanovk.core.di

object DI {
    private lateinit var featureManager: FeatureComponentContainer

    fun init(featureComponentContainer: FeatureComponentContainer) {
        featureManager = featureComponentContainer
    }

    fun <T> getFeature(key: Class<T>): T {
        return featureManager.getFeature(key)
    }

    fun <T> getDependency(key: Class<T>): T {
        return featureManager.getFeature(key)
    }

    fun <T, E> getComponent(key: Class<T>, returnType: Class<E>): E {
        val feature = getFeature(key)
        return if (returnType.isInstance(feature)) {
            returnType.cast(feature)
                ?: throw IllegalStateException("Component with key $key doesn't exist")
        } else {
            throw IllegalStateException("Component with key $key should be instance of $returnType")
        }
    }

    fun <T, E> getInternalFeature(key: Class<T>, returnType: Class<E>): E {
        val feature = getFeature(key)
        return if (returnType.isInstance(feature)) {
            returnType.cast(feature)
                ?: throw IllegalStateException("Internal feature with key $key doesn't exist")
        } else {
            throw IllegalStateException("Internal feature with key $key should be instance of $returnType")
        }
    }

}