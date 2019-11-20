package ru.cherepanovk.core_domain_impl

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import ru.cherepanovk.core_domain_api.data.AlarmApi
import ru.cherepanovk.core_domain_api.data.AlarmReminder
import ru.cherepanovk.core_domain_impl.notifications.NotificationReceiver
import ru.cherepanovk.core_domain_impl.notifications.NotificationService
import javax.inject.Inject


class CallBackAlarm @Inject constructor(
    private val alarmManager: AlarmManager,
    private val context: Context
) : AlarmApi {

    override fun createAlarm(alarmReminder: AlarmReminder) {
        val dateTimeAlarm = alarmReminder.dateTimeEvent().time
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, dateTimeAlarm, getPendingIntentForAm(alarmReminder))
    }

    private fun getPendingIntentForAm(
        alarmReminder: AlarmReminder
    ): PendingIntent {

        val data = Uri.parse(alarmReminder.dateTimeEvent().time.toString())
        val intentAm = Intent(context, NotificationReceiver::class.java).apply {
            this.data = data
            action = context.resources.getString(R.string.intent_callback_notification_service)
            putExtra(DESCRIPTION, alarmReminder.description())
            putExtra(PHONE_NUMBER,  alarmReminder.phoneNumber())
            putExtra(REMINDER_ID, alarmReminder.id())
            putExtra(CONTACT_NAME, alarmReminder.contactName())
        }
        return PendingIntent.getBroadcast(context, 0, intentAm, 0)
    }
}