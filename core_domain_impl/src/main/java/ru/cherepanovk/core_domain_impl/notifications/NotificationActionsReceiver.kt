package ru.cherepanovk.core_domain_impl.notifications

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import ru.cherepanovk.core.utils.getDialIntent
import ru.cherepanovk.core_domain_impl.NOTIFICATION_ID
import ru.cherepanovk.core_domain_impl.PHONE_NUMBER

class NotificationActionsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        openDialActivity(context, intent)
        cancelNotification(context, intent)
    }

    private fun cancelNotification(context: Context, intent: Intent) {
        val notificationId = intent.getIntExtra(NOTIFICATION_ID, 0)
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(notificationId)
    }

    private fun openDialActivity(context: Context, intent: Intent) {
        val phoneNumber = intent.getStringExtra(PHONE_NUMBER)
        context.startActivity(getDialIntent(phoneNumber))
    }
}
