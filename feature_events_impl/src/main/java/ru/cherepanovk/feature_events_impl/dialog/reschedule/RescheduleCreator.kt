package ru.cherepanovk.feature_events_impl.dialog.reschedule

import ru.cherepanovk.core_db_api.data.models.Reschedule
import ru.cherepanovk.core_db_api.data.models.RescheduleUnit
import javax.inject.Inject

class RescheduleCreator @Inject constructor() {
    fun createReschedule(rescheduleUnitType: RescheduleUnitType, number: Int): Reschedule =
        Reschedule(
            number = number,
            unit = getUnit(rescheduleUnitType)
        )

    private fun getUnit(rescheduleUnitType: RescheduleUnitType): RescheduleUnit =
        when (rescheduleUnitType) {
            RescheduleUnitType.MINUTES -> RescheduleUnit.MINUTES
            RescheduleUnitType.HOURS -> RescheduleUnit.HOURS
            RescheduleUnitType.DAYS -> RescheduleUnit.DAYS
        }
}