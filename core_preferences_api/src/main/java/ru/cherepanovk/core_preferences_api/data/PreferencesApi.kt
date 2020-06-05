package ru.cherepanovk.core_preferences_api.data

interface PreferencesApi {
    fun isFirstStart(): Boolean

    fun setFirstStart()

    fun setGoogleAccount(account: String?)

    fun getGoogleAccount(): String
}