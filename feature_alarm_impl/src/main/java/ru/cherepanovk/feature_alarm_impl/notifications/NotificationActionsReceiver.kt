package ru.cherepanovk.feature_alarm_impl.notifications

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import ru.cherepanovk.core.utils.getDialIntent
import ru.cherepanovk.feature_alarm_impl.notifications.NotificationParams.Companion.NOTIFICATION_ID_DEFAULT

class NotificationActionsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notificationParams = NotificationParams.fromBundle(intent.extras)
        openDialActivity(context, notificationParams)
        cancelNotification(context, notificationParams)
    }

    private fun cancelNotification(context: Context, params: NotificationParams?) {

        val notificationId = params?.notificationId ?: NOTIFICATION_ID_DEFAULT
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(notificationId)
    }

    private fun openDialActivity(context: Context, params: NotificationParams?) {
        //close notification panel
        context.sendBroadcast(Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS))

        val phoneNumber = params?.phoneNumber
        context.startActivity(getDialIntent(phoneNumber))

    }
}
