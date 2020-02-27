package ru.cherepanovk.core_db_impl.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class GoogleAccountEntity(
    @PrimaryKey
    private val id: Int = 0,

    val email: String
)