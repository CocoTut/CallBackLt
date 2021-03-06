package ru.cherepanovk.feature_events_impl.events.domain

import ru.cherepanovk.core.config.GooglePlayServicesAvailability
import ru.cherepanovk.core.exception.ErrorHandler
import ru.cherepanovk.core.interactor.UseCase
import ru.cherepanovk.core_preferences_api.data.PreferencesApi
import javax.inject.Inject

class AskGoogleAccount @Inject constructor(
    errorHandler: ErrorHandler,
    private val preferencesApi: PreferencesApi,
    private val googlePlayServicesAvailability: GooglePlayServicesAvailability
) : UseCase<Boolean, UseCase.None>(errorHandler) {
    override suspend fun run(params: None): Boolean {
        preferencesApi.isFirstStart().run {
            preferencesApi.setFirstStart()
            return this && googlePlayServicesAvailability.isGooglePlayServicesAvailable()
        }
    }
}