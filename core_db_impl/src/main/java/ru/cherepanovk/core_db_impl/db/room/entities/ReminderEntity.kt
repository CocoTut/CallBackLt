package ru.cherepanovk.core_db_impl.db.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

const val PHONE_REGEX_PATTERN = "[^A-Za-z0-9]"

@Entity
class ReminderEntity(
    @PrimaryKey(autoGenerate = false)
     val id: String,
     val phoneNumber: String = "",
     val description: String = "",
     val contactName: String,
     val dateTimeEvent: Date,
     val phoneNumberSearch: String = ""
)