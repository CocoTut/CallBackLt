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


        val intentAm = Intent(context, NotificationReceiver::class.java)
        val data = Uri.parse(alarmReminder.dateTimeEvent().time.toString())
        intentAm.data = data
        intentAm.action = context.resources.getString(R.string.intent_callback_notification_service)
        intentAm.putExtra(DESCRIPTION, alarmReminder.description())
        intentAm.putExtra(PHONE_NUMBER,  alarmReminder.phoneNumber())
        intentAm.putExtra(REMINDER_ID, alarmReminder.id())
        intentAm.putExtra(CONTACT_NAME, alarmReminder.contactName())

        return PendingIntent.getBroadcast(context, 0, intentAm, 0)
    }
}