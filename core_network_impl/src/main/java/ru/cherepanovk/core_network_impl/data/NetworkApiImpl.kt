package ru.cherepanovk.core_network_impl.data

import ru.cherepanovk.core_network_api.data.NetworkApi
import ru.cherepanovk.core_network_impl.api.GoogleCalendarApi
import javax.inject.Inject

class NetworkApiImpl @Inject constructor(
    private val api: GoogleCalendarApi
) : NetworkApi {
    override suspend fun calendarAuth() {
         api.authCalendar()
    }
}