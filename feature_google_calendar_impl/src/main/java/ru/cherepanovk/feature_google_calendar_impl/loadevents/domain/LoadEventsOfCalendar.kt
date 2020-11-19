package ru.cherepanovk.feature_google_calendar_impl.loadevents.domain

import com.google.api.client.util.DateTime
import ru.cherepanovk.core.exception.ErrorHandler
import ru.cherepanovk.core.interactor.UseCase
import ru.cherepanovk.core_preferences_api.data.PreferencesApi
import ru.cherepanovk.feature_google_calendar_impl.data.events.GoogleCalendarEventsManager
import java.util.*
import javax.inject.Inject

class LoadEventsOfCalendar @Inject constructor(
    errorHandler: ErrorHandler,
    private val preferencesApi: PreferencesApi,
    private val googleCalendarEventsManager: GoogleCalendarEventsManager
) : UseCase<Unit, LoadEventsOfCalendar.Params>(errorHandler) {
    override suspend fun run(params: Params): Unit {
        googleCalendarEventsManager.loadEvents(
            account = preferencesApi.getGoogleAccount(),
            startDate = params.startDate,
            endDate = params.endTime
        )
    }

    class Params(
        val startDate: Date,
        val endTime: Date
    )
}