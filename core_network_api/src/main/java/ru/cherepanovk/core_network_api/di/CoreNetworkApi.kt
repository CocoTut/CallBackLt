package ru.cherepanovk.core_network_api.di

import ru.cherepanovk.core_network_api.data.NetworkApi

interface CoreNetworkApi {
    fun getNetworkApi(): NetworkApi
}