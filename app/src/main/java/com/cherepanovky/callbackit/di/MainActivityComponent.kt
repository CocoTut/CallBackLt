package com.cherepanovky.callbackit.di

import com.cherepanovky.callbackit.CallBackItMainActivity
import dagger.Component
import ru.cherepanovk.core.di.dependencies.AppConfigProvider
import ru.cherepanovk.core.di.dependencies.RootViewProvider
import ru.cherepanovk.core.di.viewmodel.ViewModelModule
import ru.cherepanovk.core_preferences_api.di.CorePreferencesApi
import ru.cherepanovk.feature_alarm_api.di.FeatureAlarmApi

@Component(
   modules = [MainActivityModule::class, ViewModelModule::class],
    dependencies = [
        FeatureAlarmApi::class,
        CorePreferencesApi::class,
        AppConfigProvider::class
    ]
)
interface MainActivityComponent : RootViewProvider {
    fun inject(mainActivity: CallBackItMainActivity)
}