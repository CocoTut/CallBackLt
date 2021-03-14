package ru.cherepanovk.core.di

interface FeatureComponentHolder<out T> {
    fun getFeatureComponent(): T
}