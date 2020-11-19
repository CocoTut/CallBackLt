package ru.cherepanovk.core_db_impl.db.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

const val DEFAULT_ID = 0
@Entity
class Preferences(
    @PrimaryKey
    val id: Int = DEFAULT_ID,

    val emailAccount: String,

    val firstStart: Boolean,

    val ringtoneUri: String?,

    val observeIncomingCalls: Boolean,

    val observeOutgoingCalls: Boolean,

    val observeIncomingMissedCalls: Boolean
)