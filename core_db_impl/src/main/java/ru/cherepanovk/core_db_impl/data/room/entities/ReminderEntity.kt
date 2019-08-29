package ru.cherepanovk.core_db_impl.data.room.entities

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import ru.cherepanovk.core_db_api.data.Reminder
import java.util.*

@Entity
class ReminderEntity(
    @PrimaryKey(autoGenerate = false)
     val id: String,
     val phoneNumber: String = "",
     val description: String = "",
     val contactName: String,
     val dateTimeEvent: Date
) : Reminder {
    @Ignore
    override fun id(): String = id
    @Ignore
    override fun description() = description
    @Ignore
    override fun phoneNumber() = phoneNumber
    @Ignore
    override fun contactName() = contactName
    @Ignore
    override fun dateTimeEvent() = dateTimeEvent
}