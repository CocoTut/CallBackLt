package ru.cherepanovk.feature_events_impl.event.dialog

import android.os.Bundle
import ru.cherepanovk.core.utils.OpenParams

class DialogDeleteParams(
    val reminderId: String? = null
) : OpenParams {

    override fun toBundle(bundle: Bundle?): Bundle {
        return Bundle().apply {
            putString(REMINDER_ID, reminderId)
        }
    }

    companion object : OpenParams.Companion<DialogDeleteParams> {
        override fun fromBundle(bundle: Bundle?): DialogDeleteParams? {
            return DialogDeleteParams(
                reminderId = bundle?.getString(REMINDER_ID)
            )
        }

        private const val REMINDER_ID = "reminderId"
    }
}