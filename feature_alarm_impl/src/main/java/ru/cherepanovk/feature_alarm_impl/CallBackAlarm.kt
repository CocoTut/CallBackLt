package ru.cherepanovk.feature_alarm_impl

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.AlarmManagerCompat
import ru.cherepanovk.core.config.AppConfig
import ru.cherepanovk.feature_alarm_api.data.AlarmApi
import ru.cherepanovk.feature_alarm_api.data.AlarmReminder
import ru.cherepanovk.feature_alarm_impl.notifications.NotificationParams
import ru.cherepanovk.feature_alarm_impl.notifications.NotificationReceiver
import java.util.*
import javax.inject.Inject


class CallBackAlarm @Inject constructor(
    private val alarmManager: AlarmManager,
    private val context: Context,
    private val appConfig: AppConfig
) : AlarmApi {

    override fun createAlarm(alarmReminder: AlarmReminder) {
        val dateTimeAlarm = alarmReminder.dateTimeEvent().time

        if (alarmReminder.dateTimeEvent().time < Date().time && !appConfig.flashAlarmPastTime)
            return

        AlarmManagerCompat.setExactAndAllowWhileIdle(
            alarmManager,
            AlarmManager.RTC_WAKEUP,
            dateTimeAlarm,
            getPendingIntentForAm(alarmReminder)
        )
    }

    override fun cancelAlarm(alarmReminder: AlarmReminder) {
       alarmManager.cancel(getPendingIntentForAm(alarmReminder))
    }

    private fun getPendingIntentForAm(alarmReminder: AlarmReminder): PendingIntent {

        val data = Uri.parse(alarmReminder.dateTimeEvent().time.toString())
        val params = NotificationParams(
            phoneNumber = alarmReminder.phoneNumber(),
            reminderId = alarmReminder.id(),
            description = alarmReminder.description(),
            contactName = alarmReminder.contactName()
        )
        val intentAm = Intent(context, NotificationReceiver::class.java).apply {
            this.data = data
            action = context.resources.getString(R.string.intent_callback_notification_service)
            putExtras(params.toBundle())
        }
        return PendingIntent.getBroadcast(context, 0, intentAm, PendingIntent.FLAG_CANCEL_CURRENT)
    }
}