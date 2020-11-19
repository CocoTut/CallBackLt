package ru.cherepanovk.core_db_impl.db.olddb


import ru.cherepanovk.core_db_api.data.models.Reschedule
import ru.cherepanovk.core_db_api.data.models.RescheduleUnit
import ru.cherepanovk.core_db_impl.db.olddb.Reschedule as OldReschedule
import javax.inject.Inject

class OldRescheduleMapper @Inject constructor() {
    fun map(from: OldReschedule) =
        Reschedule(
            id = from.id,
            number = from.getValue(),
            unit = getUnit(from.getUnit())
        )

    private fun getUnit(unit: String): RescheduleUnit {
        return when (unit) {
            "minutes" -> RescheduleUnit.MINUTES
            "hours" -> RescheduleUnit.HOURS
            else -> RescheduleUnit.DAYS
        }
    }

}