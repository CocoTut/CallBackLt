package ru.cherepanovk.feature_google_calendar_impl.data

import android.content.Context
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.http.HttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.util.ExponentialBackOff
import com.google.api.services.calendar.Calendar
import com.google.api.services.calendar.CalendarScopes
import javax.inject.Inject

class GoogleAccountManager @Inject constructor(
    private val context: Context,
    private val transport: HttpTransport,
    private val jsonFactory: JsonFactory
) {

    fun getGoogleAccountCredential(): GoogleAccountCredential {
        return GoogleAccountCredential.usingOAuth2(
            context, listOf(CalendarScopes.CALENDAR)
        )
            .setBackOff(ExponentialBackOff())
    }

    fun getGoogleCalendar(credential: GoogleAccountCredential): Calendar {
        return Calendar.Builder(
            transport, jsonFactory, credential
        )
            .setApplicationName(APP_NAME)
            .build()
    }

    fun getGoogleCalendar(account: String): Calendar {
        return Calendar.Builder(
            transport, jsonFactory, getGoogleAccountCredential().setSelectedAccountName(account)
        )
            .setApplicationName(APP_NAME)
            .build()
    }

    companion object {
        private const val APP_NAME = "Callback it"
    }
}