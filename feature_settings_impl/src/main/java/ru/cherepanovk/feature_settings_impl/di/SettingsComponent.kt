package ru.cherepanovk.feature_settings_impl.di

import dagger.Component
import ru.cherepanovk.feature_settings_api.SettingsFeatureApi

@Component(modules = [SettingsModule::class])
interface SettingsComponent : SettingsFeatureApi {
}