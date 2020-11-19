package ru.cherepanovk.core_db_impl.db.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.cherepanovk.core_db_api.data.models.RescheduleUnit

@Entity
class RescheduleEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val number: Int,
    val unit: RescheduleUnit
)

