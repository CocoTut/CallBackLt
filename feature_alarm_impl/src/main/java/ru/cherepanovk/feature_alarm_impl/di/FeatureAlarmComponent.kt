package ru.cherepanovk.feature_alarm_impl.di

import dagger.Component
import ru.cherepanovk.core.di.dependencies.ContextProvider
import ru.cherepanovk.feature_alarm_api.di.FeatureAlarmApi

@Component(
    dependencies = [
        ContextProvider::class
    ],
    modules = [CoreDomainModule::class]
)
interface FeatureAlarmComponent : FeatureAlarmApi