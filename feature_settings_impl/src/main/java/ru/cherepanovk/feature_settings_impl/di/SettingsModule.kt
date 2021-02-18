package ru.cherepanovk.feature_settings_impl.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.cherepanovk.core.di.dependencies.FeatureNavigator
import ru.cherepanovk.core.di.viewmodel.ViewModelKey
import ru.cherepanovk.core.platform.FeatureNavigatorImpl
import ru.cherepanovk.feature_settings_api.SettingsFeatureStarter
import ru.cherepanovk.feature_settings_impl.SettingsViewModel
import ru.cherepanovk.feature_settings_impl.SettingsFeatureStarterImpl

@Module
abstract class SettingsModule {
    @Binds
    abstract fun bindStarter(settingsFeatureStarterImpl: SettingsFeatureStarterImpl): SettingsFeatureStarter

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    abstract fun bindsViewModel(settingsViewModel: SettingsViewModel): ViewModel

    @Binds
    abstract fun bindFeatureNavigator(featureNavigatorImpl: FeatureNavigatorImpl): FeatureNavigator
}