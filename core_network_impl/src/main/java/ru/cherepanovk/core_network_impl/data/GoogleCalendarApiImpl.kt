package ru.cherepanovk.core_network_impl.data

import android.content.Context
import android.content.Intent
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.http.HttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.util.ExponentialBackOff
import com.google.api.services.calendar.CalendarScopes
import ru.cherepanovk.core_network_api.data.GoogleCalendarApi
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GoogleCalendarApiImpl @Inject constructor(
    private val transport: HttpTransport,
    private val jsonFactory: JsonFactory,
    private val context: Context
) : GoogleCalendarApi {

    override fun getChooseAccountIntent(): Intent {
        return getGoogleAccountCredential().newChooseAccountIntent()
    }

    private fun getGoogleAccountCredential(): GoogleAccountCredential {
        return GoogleAccountCredential.usingOAuth2(
            context, listOf(CalendarScopes.CALENDAR)
        )
            .setBackOff(ExponentialBackOff())
    }
}