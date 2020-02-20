package ru.cherepanovk.feature_alarm_impl.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import ru.cherepanovk.core.exception.CallBackItException

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val params = NotificationParams.fromBundle(intent.extras)
            ?: throw CallBackItException.CreateNotificationException

        val notificationCreator = NotificationCreator.Builder(context)
            .addCallAction(params)
            .addOpenReminderAction(params)
            .setMessage(params)
            .build()
        notificationCreator.createNotification()
    }
}
