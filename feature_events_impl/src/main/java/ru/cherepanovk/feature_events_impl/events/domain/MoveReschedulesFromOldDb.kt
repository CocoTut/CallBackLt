package ru.cherepanovk.feature_events_impl.events.domain

import ru.cherepanovk.core.exception.ErrorHandler
import ru.cherepanovk.core.interactor.UseCase
import ru.cherepanovk.core_db_api.data.RescheduleDbApi
import ru.cherepanovk.core_db_api.data.models.Reminder
import ru.cherepanovk.core_db_api.data.models.Reschedule
import ru.cherepanovk.feature_events_impl.events.data.EventsRepository
import javax.inject.Inject

class MoveReschedulesFromOldDb @Inject constructor(
    private val rescheduleDbApi: RescheduleDbApi,
    errorHandler: ErrorHandler
) : UseCase<Unit, UseCase.None>(errorHandler) {

    override suspend fun run(params: None): Unit {
        rescheduleDbApi.addReschedules(rescheduleDbApi.getOldReschedule())
    }
}