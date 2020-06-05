package ru.cherepanovk.core_db_impl.data.room.entities

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import ru.cherepanovk.core_db_api.data.Reminder
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