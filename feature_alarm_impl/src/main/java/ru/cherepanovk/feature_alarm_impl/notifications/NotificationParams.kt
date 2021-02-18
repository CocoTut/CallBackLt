package ru.cherepanovk.feature_alarm_impl.notifications

import android.os.Bundle
import ru.cherepanovk.core.utils.OpenParams

class NotificationParams(
    val phoneNumber: String? = null,
    val reminderId: String? = null,
    val notificationId: Int? = null,
    val description: String? = null,
    val contactName: String? = null,
    val alarmed: Boolean = false
) : OpenParams {

    override fun toBundle(bundle: Bundle?): Bundle {
        return Bundle().apply {
            putString(PHONE_NUMBER, phoneNumber)
            putString(REMINDER_ID, reminderId)
            putInt(NOTIFICATION_ID, notificationId ?: NOTIFICATION_ID_DEFAULT)
            putString(CONTACT_NAME, contactName)
            putString(DESCRIPTION, description)
            putBoolean(ALARMED, alarmed)
        }
    }

    companion object : OpenParams.Companion<NotificationParams> {
        override fun fromBundle(bundle: Bundle?): NotificationParams? {
            return NotificationParams(
                phoneNumber = bundle?.getString(PHONE_NUMBER),
                reminderId = bundle?.getString(REMINDER_ID),
                notificationId = bundle?.getInt(NOTIFICATION_ID) ?: NOTIFICATION_ID_DEFAULT,
                description = bundle?.getString(DESCRIPTION),
                contactName = bundle?.getString(CONTACT_NAME),
                alarmed = bundle?.getBoolean(ALARMED) ?: false
            )
        }

        private const val PHONE_NUMBER = "phoneNumber"
        private const val DESCRIPTION = "description"
        private const val REMINDER_ID = "reminderId"
        private const val CONTACT_NAME = "contactName"
        private const val NOTIFICATION_ID = "notification_id"
        private const val ALARMED = "alarmed"
        const val NOTIFICATION_ID_DEFAULT = 3827
    }
}