package com.cherepanovky.callbackit.features.events.interactor

import com.cherepanovky.callbackit.core.exception.ErrorHandler
import com.cherepanovky.callbackit.core.interactor.UseCase
import com.cherepanovky.callbackit.core.storage.olddb.Event
import com.cherepanovky.callbackit.features.events.repository.EventsRepository
import javax.inject.Inject

class GetAllEventsFromOldDb @Inject constructor(
    private val eventsRepository: EventsRepository,
    errorHandler: ErrorHandler
) : UseCase<List<Event>, UseCase.None>(errorHandler) {

    override suspend fun run(params: None): List<Event> {
       return eventsRepository.getAllEventsFromOldBase()
    }
}