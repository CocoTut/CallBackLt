package com.cherepanovky.callbackit.features.events.interactor

import com.cherepanovky.callbackit.core.exception.ErrorHandler
import com.cherepanovky.callbackit.core.interactor.UseCase
import com.cherepanovky.callbackit.features.events.repository.EventsRepository
import ru.cherepanovk.core_db_api.data.Reminder
import javax.inject.Inject

class GetRemindersFromDb @Inject constructor(
    private val eventsRepository: EventsRepository,
    errorHandler: ErrorHandler
) : UseCase<List<Reminder>, UseCase.None>(errorHandler) {

    override suspend fun run(params: None): List<Reminder> {
       return eventsRepository.getRemindersFromDb()
    }
}