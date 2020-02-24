package ru.cherepanovk.core_network_impl.api

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Url

interface GoogleCalendarApi {
    @POST
    suspend fun authCalendar(
        @Url authUrl: String = "https://accounts.google.com/o/oauth2/v2/auth",
        @Query("scope") scope: String = "https://www.googleapis.com/auth/calendar",
        @Query("response_type") response_type: String = "code",
        @Query("redirect_uri") redirect_uri: String = "com.cherepanovky.callbackit:/oauth2redirect",
        @Query("client_id") client_id: String = "157170628530-mkoq9u2ev1idvv7qm762uelesdm0s5a6.apps.googleusercontent.com"
    )

    companion object {
        const val BASE_URL = "https://www.ecb.europa.eu/"
    }
}