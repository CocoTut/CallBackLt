package ru.cherepanovk.core_db_impl.data.reschedules

import ru.cherepanovk.core_db_api.data.models.Reschedule
import ru.cherepanovk.core_db_impl.db.room.entities.RescheduleEntity
import javax.inject.Inject

class RescheduleMapper @Inject constructor() {
    fun map(from: Reschedule): RescheduleEntity =
        RescheduleEntity(
            number = from.number,
            unit = from.unit
        )
}