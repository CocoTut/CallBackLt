package ru.cherepanovk.core_network_api.di

import ru.cherepanovk.core_network_api.data.GoogleCalendarApi

interface CoreNetworkApi {
    fun getNetworkApi(): GoogleCalendarApi
}