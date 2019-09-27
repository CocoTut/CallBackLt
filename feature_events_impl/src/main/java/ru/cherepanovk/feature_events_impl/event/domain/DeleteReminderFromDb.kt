package ru.cherepanovk.feature_events_impl.event.domain

import ru.cherepanovk.core.exception.ErrorHandler
import ru.cherepanovk.core.interactor.UseCase
import ru.cherepanovk.core_db_api.data.Reminder
import ru.cherepanovk.feature_events_impl.event.data.EventRepository
import javax.inject.Inject

class DeleteReminderFromDb @Inject constructor(
    private val eventRepository: EventRepository,
    errorHandler: ErrorHandler
) : UseCase<Unit, String>(errorHandler) {

    override suspend fun run(params: String) {
       return eventRepository.deleteReminderById(params)
    }
}