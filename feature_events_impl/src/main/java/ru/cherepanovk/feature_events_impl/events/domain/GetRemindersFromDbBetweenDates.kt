package ru.cherepanovk.feature_events_impl.events.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.cherepanovk.core.exception.ErrorHandler
import ru.cherepanovk.core.interactor.UseCase
import ru.cherepanovk.core_db_api.data.models.Reminder
import ru.cherepanovk.feature_events_impl.events.data.EventsRepository
import java.util.*
import javax.inject.Inject

class GetRemindersFromDbBetweenDates @Inject constructor(
    private val eventsRepository: EventsRepository,
    errorHandler: ErrorHandler
) : UseCase<Flow<List<Reminder>>, GetRemindersFromDbBetweenDates.Params>(errorHandler) {

    override suspend fun run(params: Params): Flow<List<Reminder>> {
       return eventsRepository.getRemindersBetweenDates(params.startDate, params.endDate)
           .map {reminders-> reminders.sortedByDescending { it.dateTimeEvent } }
    }

    class Params(
       val startDate: Date,
       val endDate: Date
    )
}