package ru.cherepanovk.core.di.dependencies

import ru.cherepanovk.core.di.FeatureComponentContainer

interface FeatureContainerProvider {
    fun getContainer(): FeatureComponentContainer
}