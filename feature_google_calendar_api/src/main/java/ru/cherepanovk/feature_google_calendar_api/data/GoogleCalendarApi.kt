package ru.cherepanovk.feature_google_calendar_api.data

import android.content.Intent

interface GoogleCalendarApi {
    fun getChooseAccountIntent(): Intent
}