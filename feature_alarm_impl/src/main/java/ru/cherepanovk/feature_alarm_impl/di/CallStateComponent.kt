package ru.cherepanovk.feature_alarm_impl.di

import dagger.Component
import ru.cherepanovk.core_db_api.di.CoreDbApi

@Component(
    dependencies = [CoreDbApi::class],
    modules = [CallStateModule::class]
)
interface CallStateComponent  {
}