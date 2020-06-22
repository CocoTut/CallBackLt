package ru.cherepanovk.feature_events_impl.events.domain

import ru.cherepanovk.core.exception.ErrorHandler
import ru.cherepanovk.core.interactor.UseCase
import ru.cherepanovk.feature_events_impl.events.data.GoogleAccountRepository
import javax.inject.Inject

class GetGoogleAccountFromOldDb @Inject constructor(
    private val googleAccountRepository: GoogleAccountRepository,
    errorHandler: ErrorHandler
) : UseCase<String?, UseCase.None>(errorHandler) {

    override suspend fun run(params: None): String?{
        return googleAccountRepository.getGoogleAccount()
    }
}