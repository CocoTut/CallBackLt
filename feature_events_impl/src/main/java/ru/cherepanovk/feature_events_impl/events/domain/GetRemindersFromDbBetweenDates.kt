package ru.cherepanovk.feature_events_impl.events.domain

import ru.cherepanovk.core.exception.ErrorHandler
import ru.cherepanovk.core.interactor.UseCase
import ru.cherepanovk.core_db_api.data.Reminder
import ru.cherepanovk.feature_events_impl.events.data.EventsRepository
import java.util.*
import javax.inject.Inject

class GetRemindersFromDbBetweenDates @Inject constructor(
    private val eventsRepository: EventsRepository,
    errorHandler: ErrorHandler
) : UseCase<List<Reminder>, GetRemindersFromDbBetweenDates.Params>(errorHandler) {

    override suspend fun run(params: Params): List<Reminder> {
       return eventsRepository.getRemindersBetweenDates(params.startDate, params.endDate)
           .sortedByDescending { it.dateTimeEvent() }
    }

    class Params(
       val startDate: Date,
       val endDate: Date
    )
}