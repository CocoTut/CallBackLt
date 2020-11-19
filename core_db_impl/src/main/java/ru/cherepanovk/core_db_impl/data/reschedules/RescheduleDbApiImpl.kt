package ru.cherepanovk.core_db_impl.data.reschedules

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.cherepanovk.core_db_api.data.RescheduleDbApi
import ru.cherepanovk.core_db_api.data.models.Reschedule
import ru.cherepanovk.core_db_impl.db.olddb.LocalBase
import ru.cherepanovk.core_db_impl.db.olddb.OldRescheduleMapper
import ru.cherepanovk.core_db_impl.db.room.dao.RescheduleDao
import javax.inject.Inject

class RescheduleDbApiImpl @Inject constructor(
    private val entityRescheduleMapper: EntityRescheduleMapper,
    private val rescheduleMapper: RescheduleMapper,
    private val rescheduleDao: RescheduleDao,
    private val localBase: LocalBase,
    private val oldRescheduleMapper: OldRescheduleMapper
) : RescheduleDbApi {

    override suspend fun getReschedules(): Flow<List<Reschedule>> =
        rescheduleDao.getAllReschedules().map { reschedules ->
            reschedules.map { entityRescheduleMapper.map(it) }
        }

    override suspend fun addReschedule(reschedule: Reschedule) {
        rescheduleDao.addReschedule(rescheduleMapper.map(reschedule))
    }

    override suspend fun addReschedules(reschedules: List<Reschedule>) {
        rescheduleDao.addReschedules(reschedules.map { rescheduleMapper.map(it) })
    }

    override suspend fun deleteReschedule(id: Int) {
        rescheduleDao.deleteRescheduleById(id)
    }

    override fun getOldReschedule(): List<Reschedule>  =
        localBase.listReschedule.map { oldRescheduleMapper.map(it) }
}