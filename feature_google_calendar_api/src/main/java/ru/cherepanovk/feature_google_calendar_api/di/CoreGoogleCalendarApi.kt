package ru.cherepanovk.feature_google_calendar_api.di

import ru.cherepanovk.feature_google_calendar_api.data.GoogleAccountFeatureStarter
import ru.cherepanovk.feature_google_calendar_api.data.GoogleCalendarApi

interface CoreGoogleCalendarApi {
    fun getNetworkApi(): GoogleCalendarApi

    fun getGoogleAccountFeatureStarter(): GoogleAccountFeatureStarter
}