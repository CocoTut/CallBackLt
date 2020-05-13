package ru.cherepanovk.feature_google_calendar_api.data

import android.content.Intent
import androidx.fragment.app.Fragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface GoogleCalendarApi {
    fun savingAccount(): StateFlow<Boolean>
}