package ru.cherepanovk.feature_events_impl.event

import android.os.Bundle
import ru.cherepanovk.core.utils.OpenParams

class EventOpenParams (
    val phoneNumber: String? = null,
    val reminderId: String?,
    val openReschedule: Boolean? = null
) : OpenParams {

    override fun toBundle(bundle: Bundle?): Bundle {
        return Bundle().apply {
            putString(PHONE_NUMBER, phoneNumber)
            putString(REMINDER_ID, reminderId)
            putBoolean(RESCHEDULE_EVENT, openReschedule ?: false)
        }
    }

    companion object : OpenParams.Companion<EventOpenParams> {
        override fun fromBundle(bundle: Bundle?): EventOpenParams? {
            return EventOpenParams(
                phoneNumber = bundle?.getString(PHONE_NUMBER),
                reminderId = bundle?.getString(REMINDER_ID),
                openReschedule = bundle?.getBoolean(RESCHEDULE_EVENT)
            )
        }

        const val PHONE_NUMBER = "phoneNumber"
        const val REMINDER_ID = "reminderId"
        const val RESCHEDULE_EVENT = "rescheduleEvent"
    }
}