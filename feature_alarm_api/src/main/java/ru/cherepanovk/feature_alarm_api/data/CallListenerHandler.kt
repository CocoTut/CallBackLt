package ru.cherepanovk.feature_alarm_api.data

import android.content.Intent

interface CallListenerHandler {
    fun startCallLister(phoneStateIntent: Intent)
}