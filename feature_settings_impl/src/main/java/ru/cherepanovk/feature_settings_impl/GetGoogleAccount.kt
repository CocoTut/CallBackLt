package ru.cherepanovk.feature_settings_impl

import ru.cherepanovk.core.exception.ErrorHandler
import ru.cherepanovk.core.interactor.UseCase
import ru.cherepanovk.core_preferences_api.data.PreferencesApi
import javax.inject.Inject

class GetGoogleAccount @Inject constructor(
    errorHandler: ErrorHandler,
    private val preferences: PreferencesApi
) : UseCase<String, UseCase.None>(errorHandler) {
    override suspend fun run(params: None): String {
        return preferences.getGoogleAccount()
    }
}