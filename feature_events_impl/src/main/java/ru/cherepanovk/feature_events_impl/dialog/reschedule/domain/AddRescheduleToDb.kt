package ru.cherepanovk.feature_events_impl.dialog.reschedule.domain

import ru.cherepanovk.core.exception.ErrorHandler
import ru.cherepanovk.core.interactor.UseCase
import ru.cherepanovk.core_db_api.data.models.Reschedule
import ru.cherepanovk.feature_events_impl.dialog.reschedule.data.RescheduleRepository
import javax.inject.Inject

class AddRescheduleToDb @Inject constructor(
    private val rescheduleRepository: RescheduleRepository,
    errorHandler: ErrorHandler
) : UseCase<Unit, Reschedule>(errorHandler) {

    override suspend fun run(params: Reschedule){
        rescheduleRepository.addReschedule(params)
    }

}