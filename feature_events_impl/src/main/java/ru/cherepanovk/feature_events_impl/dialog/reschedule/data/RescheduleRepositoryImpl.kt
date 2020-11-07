package ru.cherepanovk.feature_events_impl.dialog.reschedule.data

import kotlinx.coroutines.flow.Flow
import ru.cherepanovk.core_db_api.data.RescheduleDbApi
import ru.cherepanovk.core_db_api.data.models.Reschedule
import javax.inject.Inject

class RescheduleRepositoryImpl @Inject constructor(
    private val rescheduleDbApi: RescheduleDbApi
) : RescheduleRepository {
    override suspend fun getReschedules(): Flow<List<Reschedule>> =
        rescheduleDbApi.getReschedules()

    override suspend fun addReschedules(reschedules: List<Reschedule>) {
        rescheduleDbApi.addReschedules(reschedules)
    }

    override suspend fun deleteReschedule(id: Int) {
        rescheduleDbApi.deleteReschedule(id)
    }

    override suspend fun addReschedule(reschedule: Reschedule) {
        rescheduleDbApi.addReschedule(reschedule)
    }
}