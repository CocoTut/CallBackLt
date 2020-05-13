package ru.cherepanovk.feature_google_calendar_impl.addaccount.domain

import ru.cherepanovk.core.exception.ErrorHandler
import ru.cherepanovk.core.interactor.UseCase
import ru.cherepanovk.feature_google_calendar_api.data.GoogleAccountAvailability
import ru.cherepanovk.feature_google_calendar_api.data.GoogleCalendarApi
import ru.cherepanovk.feature_google_calendar_impl.data.AccountPermissionManager
import javax.inject.Inject

class CheckGoogleAccount @Inject constructor(
    errorHandler: ErrorHandler,
    private val accountPermissionManager: AccountPermissionManager
) : UseCase<GoogleAccountAvailability,String>(errorHandler) {
    override suspend fun run(params: String): GoogleAccountAvailability {
        return accountPermissionManager.isAccountAvailable(params)
    }
}
