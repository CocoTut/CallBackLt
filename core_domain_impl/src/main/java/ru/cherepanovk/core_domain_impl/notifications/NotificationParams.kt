package ru.cherepanovk.core_domain_impl.notifications

import android.os.Bundle
import ru.cherepanovk.core.utils.OpenParams

class NotificationParams (
    val phoneNumber: String?,
    val reminderId: String?,
    val notificationId: Int? = null,
    val description: String?,
    val contactName: String?
) : OpenParams {

    override fun toBundle(bundle: Bundle?): Bundle {
        return Bundle().apply {
            putString(PHONE_NUMBER, phoneNumber)
            putString(REMINDER_ID, reminderId)
            putInt(NOTIFICATION_ID, notificationId ?: NOTIFICATION_ID_DEFAULT)
            putString(CONTACT_NAME, contactName)
            putString(DESCRIPTION, description)
        }
    }

    companion object : OpenParams.Companion<NotificationParams> {
        override fun fromBundle(bundle: Bundle?): NotificationParams? {
            return NotificationParams(
                phoneNumber = bundle?.getString(PHONE_NUMBER),
                reminderId = bundle?.getString(REMINDER_ID),
                notificationId = bundle?.getInt(NOTIFICATION_ID) ?: NOTIFICATION_ID_DEFAULT,
                description = bundle?.getString(DESCRIPTION),
                contactName =  bundle?.getString(CONTACT_NAME)
            )
        }

        private const val PHONE_NUMBER = "phoneNumber"
        private const val DESCRIPTION = "description"
        private const val REMINDER_ID = "reminderId"
        private const val CONTACT_NAME = "contactName"
        private const val NOTIFICATION_ID = "notification_id"
        const val NOTIFICATION_ID_DEFAULT = 3827
    }
}