package ru.cherepanovk.feature_events_impl.dialog.reschedule

import ru.cherepanovk.core_db_api.data.models.Reschedule
import ru.cherepanovk.core_db_api.data.models.RescheduleUnit
import javax.inject.Inject

class RescheduleMapper @Inject constructor() {
    fun map(from: Reschedule, onDeleteClick: (Int) -> Unit): RescheduleItem =
        RescheduleItem(
            id = from.id,
            number = from.number,
            unit = getUnit(from.unit),
            onMenuDeleteClick = onDeleteClick
        )

    private fun getUnit(unit: RescheduleUnit): RescheduleUnitType  =
        when(unit) {
            RescheduleUnit.MINUTES -> RescheduleUnitType.MINUTES
            RescheduleUnit.HOURS -> RescheduleUnitType.HOURS
            RescheduleUnit.DAYS -> RescheduleUnitType.DAYS
        }
}