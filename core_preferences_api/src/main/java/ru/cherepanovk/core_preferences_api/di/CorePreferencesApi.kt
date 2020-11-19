package ru.cherepanovk.core_preferences_api.di

import ru.cherepanovk.core_preferences_api.data.PreferencesApi

interface CorePreferencesApi {
    fun getPreferencesApi(): PreferencesApi
}