package ru.cherepanovk.feature_events_impl.events.domain

import android.content.Intent
import ru.cherepanovk.core.exception.ErrorHandler
import ru.cherepanovk.core.interactor.UseCase
import ru.cherepanovk.core_db_api.data.DbApi
import ru.cherepanovk.feature_google_calendar_api.data.GoogleCalendarApi
import javax.inject.Inject

class AskGoogleAccount@Inject constructor(
    errorHandler: ErrorHandler,
    private val dataBase: DbApi,
    private val googleCalendarApi: GoogleCalendarApi
) : UseCase<Intent?, UseCase.None>(errorHandler) {
    override suspend fun run(params: None): Intent? {
       return if (dataBase.isFirstStart())
            googleCalendarApi.getChooseAccountIntent()
        else
           null

    }
}