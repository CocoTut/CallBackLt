package ru.cherepanovk.core_domain_impl.notifications

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import androidx.core.app.NotificationCompat
import ru.cherepanovk.core_domain_api.data.NotificationChannelCreator
import ru.cherepanovk.core_domain_impl.*
import javax.inject.Inject

class CallListenerNotificationCreatorImpl @Inject constructor(
    private val context: Context,
    private val notificationChannelCreator: NotificationChannelCreator
) : CallListenerNotificationCreator {

    override fun getNotification(stopPendingIntent: PendingIntent): Notification {
        val notificationBuilder =
            NotificationCompat.Builder(context, notificationChannelCreator.getMuteChannelId())
                .setSmallIcon(R.drawable.baseline_done)
                .setContentTitle(null)
                .addAction(R.drawable.ic_call, context.getString(R.string.stop_foreground), stopPendingIntent)
        return notificationBuilder.build()
    }

}