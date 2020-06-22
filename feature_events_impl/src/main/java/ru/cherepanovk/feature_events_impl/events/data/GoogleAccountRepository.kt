package ru.cherepanovk.feature_events_impl.events.data

interface GoogleAccountRepository {
    fun getGoogleAccount(): String?
}