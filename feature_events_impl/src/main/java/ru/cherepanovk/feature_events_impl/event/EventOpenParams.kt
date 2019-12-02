package ru.cherepanovk.feature_events_impl.event

import android.os.Bundle
import ru.cherepanovk.core.utils.OpenParams

class EventOpenParams (
    val phoneNumber: String? = null,
    val reminderId: String?
) : OpenParams {

    override fun toBundle(bundle: Bundle?): Bundle {
        return Bundle().apply {
            putString(PHONE_NUMBER, phoneNumber)
            putString(REMINDER_ID, reminderId)
        }
    }

    companion object : OpenParams.Companion<EventOpenParams> {
        override fun fromBundle(bundle: Bundle?): EventOpenParams? {
            return EventOpenParams(
                phoneNumber = bundle?.getString(PHONE_NUMBER),
                reminderId = bundle?.getString(REMINDER_ID)
            )
        }

        private const val PHONE_NUMBER = "phoneNumber"
        const val REMINDER_ID = "reminderId"
    }
}