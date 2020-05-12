package ru.cherepanovk.feature_google_calendar_api.data

import android.content.Intent

class GoogleAccountAvailability(
    val available: Boolean,
    val userAuthIntent: Intent?
)