package ru.cherepanovk.feature_settings_impl.di

import dagger.Binds
import dagger.Module
import ru.cherepanovk.feature_settings_api.SettingsFeatureStarter
import ru.cherepanovk.feature_settings_impl.SettingsFeatureStarterImpl

@Module
abstract class SettingsModule {
    @Binds
    abstract fun bindStarter(settingsFeatureStarterImpl: SettingsFeatureStarterImpl): SettingsFeatureStarter
}