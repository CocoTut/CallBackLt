package ru.cherepanovk.core_domain_impl.di

import dagger.Component
import ru.cherepanovk.core.di.dependencies.ContextProvider
import ru.cherepanovk.core_db_api.data.DbApi
import ru.cherepanovk.core_db_api.di.CoreDbApi

@Component(
    dependencies = [CoreDbApi::class],
    modules = [CallStateModule::class]
)
interface CallStateComponent  {
}