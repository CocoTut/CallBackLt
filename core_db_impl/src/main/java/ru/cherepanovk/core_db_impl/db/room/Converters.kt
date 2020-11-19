package ru.cherepanovk.core_db_impl.db.room

import androidx.room.TypeConverter
import ru.cherepanovk.core_db_api.data.models.RescheduleUnit
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun rescheduleUnitToInt(unit: RescheduleUnit): Int =
        when (unit) {
            RescheduleUnit.MINUTES -> 0
            RescheduleUnit.HOURS -> 1
            RescheduleUnit.DAYS -> 3
        }

    @TypeConverter
    fun intToRescheduleUnit(unit: Int): RescheduleUnit =
        when (unit) {
            0 -> RescheduleUnit.MINUTES
            1 -> RescheduleUnit.HOURS
            3 -> RescheduleUnit.DAYS
            else -> throw IllegalArgumentException("Unknown reschedule type")
        }
}