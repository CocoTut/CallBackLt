package ru.cherepanovk.feature_events_impl.event.domain

import ru.cherepanovk.core.exception.CallBackItException
import ru.cherepanovk.core.exception.ErrorHandler
import ru.cherepanovk.core.interactor.UseCase
import ru.cherepanovk.core_db_api.data.models.Reminder
import ru.cherepanovk.feature_events_impl.event.data.EventRepository
import javax.inject.Inject

class GetReminderFromDb @Inject constructor(
    private val eventRepository: EventRepository,
    errorHandler: ErrorHandler
) : UseCase<Reminder, String>(errorHandler) {

    override suspend fun run(params: String): Reminder {
       return eventRepository.getReminderFromDb(params) ?:  throw CallBackItException.NoSuchReminder
    }
}