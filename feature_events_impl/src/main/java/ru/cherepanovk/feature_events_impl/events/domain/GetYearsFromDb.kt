package ru.cherepanovk.feature_events_impl.events.domain

import kotlinx.coroutines.flow.Flow
import ru.cherepanovk.core.exception.ErrorHandler
import ru.cherepanovk.core.interactor.UseCase
import ru.cherepanovk.feature_events_impl.events.data.EventsRepository
import javax.inject.Inject

class GetYearsFromDb @Inject constructor(
    private val eventsRepository: EventsRepository,
    errorHandler: ErrorHandler
) : UseCase<Flow<List<String>>, UseCase.None>(errorHandler) {

    override suspend fun run(params: None): Flow<List<String>> {
       return eventsRepository.getYears()
    }
}