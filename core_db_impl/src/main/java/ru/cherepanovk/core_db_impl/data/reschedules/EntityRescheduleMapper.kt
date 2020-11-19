package ru.cherepanovk.core_db_impl.data.reschedules

import ru.cherepanovk.core_db_api.data.models.Reschedule
import ru.cherepanovk.core_db_impl.db.room.entities.RescheduleEntity
import javax.inject.Inject

class EntityRescheduleMapper @Inject constructor() {
    fun map(from: RescheduleEntity): Reschedule =
        Reschedule(
            id = from.id,
            number = from.number,
            unit = from.unit
        )
}