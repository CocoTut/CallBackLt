package ru.cherepanovk.feature_events_impl.dialog.reschedule.domain

import ru.cherepanovk.core.exception.ErrorHandler
import ru.cherepanovk.core.interactor.UseCase
import ru.cherepanovk.feature_events_impl.dialog.reschedule.data.RescheduleRepository
import javax.inject.Inject

class DeleteReschedulesFromDb @Inject constructor(
    private val rescheduleRepository: RescheduleRepository,
    errorHandler: ErrorHandler
) : UseCase<Unit, Int>(errorHandler) {

    override suspend fun run(params: Int){
        rescheduleRepository.deleteReschedule(params)
    }

}