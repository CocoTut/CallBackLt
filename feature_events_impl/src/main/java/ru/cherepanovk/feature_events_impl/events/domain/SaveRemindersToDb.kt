package ru.cherepanovk.feature_events_impl.events.domain

import ru.cherepanovk.core.exception.ErrorHandler
import ru.cherepanovk.core.interactor.UseCase
import ru.cherepanovk.core_db_api.data.models.Reminder
import ru.cherepanovk.feature_events_impl.events.data.EventsRepository
import javax.inject.Inject

class SaveRemindersToDb @Inject constructor(
    private val eventsRepository: EventsRepository,
    errorHandler: ErrorHandler
) : UseCase<Unit, List<Reminder>>(errorHandler) {

    override suspend fun run(params: List<Reminder>) {
       return eventsRepository.saveRemindersToDb(params)
    }
}