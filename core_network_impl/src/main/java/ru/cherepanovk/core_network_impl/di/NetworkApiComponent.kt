package ru.cherepanovk.core_network_impl.di

import dagger.Component
import ru.cherepanovk.core_network_api.di.CoreNetworkApi

@Component(
    modules = [CoreNetworkApiModule::class]
)
interface NetworkApiComponent  : CoreNetworkApi