package ru.cherepanovk.core_domain_api.data

import android.content.Intent

interface CallListenerHandler {
    fun startCallLister(phoneStateIntent: Intent)
}