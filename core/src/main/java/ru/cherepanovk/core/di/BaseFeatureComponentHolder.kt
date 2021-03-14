package ru.cherepanovk.core.di

import java.util.concurrent.locks.ReentrantLock

abstract class BaseFeatureComponentHolder<T>(
    private val featureComponentContainer: FeatureComponentContainer
) : FeatureComponentHolder<T> {

    protected var feature: T? = null

    protected val buildFeatureLock = ReentrantLock()

    override fun getFeatureComponent(): T {
        if (feature == null) {
            buildFeatureLock.lock()
            try {
                if (feature == null) {
                    feature = buildFeature()
                }
            } finally {
                buildFeatureLock.unlock()
            }
        }
        return feature!!
    }

    protected open fun <D> getDependency(key: Class<D>?): D {
        return featureComponentContainer.getDependency(key)
    }

    protected abstract fun buildFeature(): T
}