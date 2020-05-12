package ru.cherepanovk.feature_settings_impl.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.cherepanovk.core.di.dependencies.FeatureNavigator
import ru.cherepanovk.core.di.viewmodel.ViewModelKey
import ru.cherepanovk.core.platform.FeatureNavigatorImpl
import ru.cherepanovk.feature_settings_api.SettingsFeatureStarter
import ru.cherepanovk.feature_settings_impl.SettingViewModel
import ru.cherepanovk.feature_settings_impl.SettingsFeatureStarterImpl

@Module
abstract class SettingsModule {
    @Binds
    abstract fun bindStarter(settingsFeatureStarterImpl: SettingsFeatureStarterImpl): SettingsFeatureStarter

    @Binds
    @IntoMap
    @ViewModelKey(SettingViewModel::class)
    abstract fun bindsViewModel(settingViewModel: SettingViewModel): ViewModel

    @Binds
    abstract fun bindFeatureNavigator(featureNavigatorImpl: FeatureNavigatorImpl): FeatureNavigator
}