package ru.cherepanovk.core_preferences_impl.di

import dagger.Component
import ru.cherepanovk.core.di.dependencies.ContextProvider
import ru.cherepanovk.core_preferences_api.di.CorePreferencesApi

@Component(
    modules = [CorePreferencesModule::class],
    dependencies = [ContextProvider::class]
)
interface CorePreferencesComponent : CorePreferencesApi