package ru.cherepanovk.core_db_api.data

import kotlinx.coroutines.flow.Flow
import ru.cherepanovk.core_db_api.data.models.Reschedule

interface RescheduleDbApi {
    suspend fun getReschedules(): Flow<List<Reschedule>>

    suspend fun addReschedule(reschedule: Reschedule)

    suspend fun addReschedules(reschedules: List<Reschedule>)

    suspend fun deleteReschedule(id: Int)

    fun getOldReschedule(): List<Reschedule>
}