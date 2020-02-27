package ru.cherepanovk.feature_google_calendar_api.di

import ru.cherepanovk.feature_google_calendar_api.data.GoogleCalendarApi

interface CoreNetworkApi {
    fun getNetworkApi(): GoogleCalendarApi
}