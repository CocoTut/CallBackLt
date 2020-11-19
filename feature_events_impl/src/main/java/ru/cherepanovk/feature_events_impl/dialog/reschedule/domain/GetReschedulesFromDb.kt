package ru.cherepanovk.feature_events_impl.dialog.reschedule.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.cherepanovk.core.exception.ErrorHandler
import ru.cherepanovk.core.interactor.UseCase
import ru.cherepanovk.core_db_api.data.models.Reschedule
import ru.cherepanovk.core_db_api.data.models.RescheduleUnit
import ru.cherepanovk.feature_events_impl.dialog.reschedule.data.RescheduleRepository
import javax.inject.Inject

class GetReschedulesFromDb @Inject constructor(
    private val rescheduleRepository: RescheduleRepository,
    errorHandler: ErrorHandler
) : UseCase<Flow<List<Reschedule>> , UseCase.None>(errorHandler) {

    override suspend fun run(params: None): Flow<List<Reschedule>> {
       return rescheduleRepository.getReschedules().map {
            if (it.isEmpty())
                generateReschedules()
            return@map it
        }.map {
            it.sortedWith(compareByDescending<Reschedule> { it.unit == RescheduleUnit.MINUTES }
                .thenByDescending { it.unit == RescheduleUnit.HOURS }
                .thenByDescending { it.unit == RescheduleUnit.DAYS }
                .thenBy { it.number }
            )
        }

    }
    private suspend fun generateReschedules(){
        val reschedules = arrayListOf(
            Reschedule(
                number = 1,
                unit = RescheduleUnit.MINUTES
            ),
            Reschedule(
                number = 5,
                unit = RescheduleUnit.MINUTES
            ),
            Reschedule(
                number = 15,
                unit = RescheduleUnit.MINUTES
            ),
            Reschedule(
                number = 1,
                unit = RescheduleUnit.HOURS
            ),
            Reschedule(
                number = 1,
                unit = RescheduleUnit.DAYS
            )
        )
        rescheduleRepository.addReschedules(reschedules)
    }
}