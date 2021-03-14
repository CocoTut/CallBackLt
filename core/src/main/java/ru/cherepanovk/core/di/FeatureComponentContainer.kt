package ru.cherepanovk.core.di

interface FeatureComponentContainer {
    fun <T> getFeature(key: Class<T>?): T

    fun <T> getDependency(key: Class<T>?): T
}