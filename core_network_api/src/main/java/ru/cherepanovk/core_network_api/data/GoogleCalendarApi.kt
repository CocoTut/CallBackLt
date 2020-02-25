package ru.cherepanovk.core_network_api.data

import android.content.Intent

interface GoogleCalendarApi {
    fun getChooseAccountIntent(): Intent
}