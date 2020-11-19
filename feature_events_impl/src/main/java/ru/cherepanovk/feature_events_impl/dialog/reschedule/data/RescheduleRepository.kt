package ru.cherepanovk.feature_events_impl.dialog.reschedule.data

import kotlinx.coroutines.flow.Flow
import ru.cherepanovk.core_db_api.data.models.Reschedule

interface RescheduleRepository {
    suspend fun getReschedules(): Flow<List<Reschedule>>
    suspend fun addReschedules(reschedules: List<Reschedule>)
    suspend fun deleteReschedule(id: Int)
    suspend fun addReschedule(reschedule: Reschedule)
}