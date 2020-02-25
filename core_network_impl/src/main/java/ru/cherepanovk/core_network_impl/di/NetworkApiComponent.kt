package ru.cherepanovk.core_network_impl.di

import dagger.Component
import ru.cherepanovk.core.di.dependencies.ContextProvider
import ru.cherepanovk.core_network_api.di.CoreNetworkApi
import javax.inject.Singleton

@Component(
    modules = [CoreNetworkApiModule::class],
    dependencies = [ContextProvider::class]
)
@Singleton
interface NetworkApiComponent  : CoreNetworkApi