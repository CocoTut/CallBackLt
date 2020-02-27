package com.cherepanovky.callbackit.di

import com.cherepanovky.callbackit.CallBackItMainActivity
import dagger.Component
import ru.cherepanovk.core.di.dependencies.RootViewProvider
import ru.cherepanovk.core.di.viewmodel.ViewModelModule
import ru.cherepanovk.feature_google_calendar_api.di.CoreNetworkApi
import ru.cherepanovk.feature_alarm_api.di.CoreDomainApi

@Component(
   modules = [MainActivityModule::class, ViewModelModule::class],
    dependencies = [
        CoreDomainApi::class,
        CoreNetworkApi::class
    ]
)
interface MainActivityComponent : RootViewProvider {
    fun inject(mainActivity: CallBackItMainActivity)
}