package ru.cherepanovk.feature_events_impl.dialog.reschedule

import android.os.Bundle
import ru.cherepanovk.core.utils.OpenParams

class DialogRescheduleParams(
    val reminderId: String? = null
) : OpenParams {

    override fun toBundle(bundle: Bundle?): Bundle {
        return Bundle().apply {
            putString(REMINDER_ID, reminderId)
        }
    }

    companion object : OpenParams.Companion<DialogRescheduleParams> {
        override fun fromBundle(bundle: Bundle?): DialogRescheduleParams? {
            return DialogRescheduleParams(
                reminderId = bundle?.getString(REMINDER_ID)
            )
        }

        private const val REMINDER_ID = "reminderId"
    }
}