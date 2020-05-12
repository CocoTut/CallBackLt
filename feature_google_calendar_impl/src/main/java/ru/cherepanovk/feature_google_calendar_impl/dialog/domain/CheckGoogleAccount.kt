package ru.cherepanovk.feature_google_calendar_impl.dialog.domain

import ru.cherepanovk.core.exception.ErrorHandler
import ru.cherepanovk.core.interactor.UseCase
import ru.cherepanovk.feature_google_calendar_api.data.GoogleAccountAvailability
import ru.cherepanovk.feature_google_calendar_api.data.GoogleCalendarApi
import javax.inject.Inject

class CheckGoogleAccount @Inject constructor(
    errorHandler: ErrorHandler,
    private val googleCalendarApi: GoogleCalendarApi
) : UseCase<GoogleAccountAvailability,String>(errorHandler) {
    override suspend fun run(params: String): GoogleAccountAvailability {
        return googleCalendarApi.isAccountAvailable(params)
    }
}
