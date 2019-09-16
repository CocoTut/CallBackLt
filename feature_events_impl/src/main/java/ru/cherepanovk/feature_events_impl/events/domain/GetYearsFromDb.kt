package ru.cherepanovk.feature_events_impl.events.domain

import ru.cherepanovk.core.exception.ErrorHandler
import ru.cherepanovk.core.interactor.UseCase
import ru.cherepanovk.core_db_api.data.Reminder
import ru.cherepanovk.feature_events_impl.events.data.EventsRepository
import java.util.*
import javax.inject.Inject

class GetYearsFromDb @Inject constructor(
    private val eventsRepository: EventsRepository,
    errorHandler: ErrorHandler
) : UseCase<List<String>, UseCase.None>(errorHandler) {

    override suspend fun run(params: None): List<String> {
       return eventsRepository.getYears()
    }
}