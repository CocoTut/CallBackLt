package ru.cherepanovk.core.di

interface FeaturesInitializer {
    fun initialize(): Map<Class<*>, FeatureComponentHolder<*>>
}