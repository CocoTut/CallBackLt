package ru.cherepanovk.feature_alarm_impl.notifications

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import androidx.core.app.NotificationCompat
import ru.cherepanovk.feature_alarm_api.data.NotificationChannelCreator
import ru.cherepanovk.feature_alarm_impl.*
import javax.inject.Inject

class CallListenerNotificationCreatorImpl @Inject constructor(
    private val context: Context,
    private val notificationChannelCreator: NotificationChannelCreator
) : CallListenerNotificationCreator {

    override fun getNotificationWithStop(stopPendingIntent: PendingIntent): Notification {
        val notificationBuilder =
            NotificationCompat.Builder(context, notificationChannelCreator.getMuteChannelId())
                .setSmallIcon(R.drawable.ic_cached_white_24dp)
                .setContentTitle(null)
                .addAction(R.drawable.ic_call, context.getString(R.string.stop_foreground), stopPendingIntent)
        return notificationBuilder.build()
    }

    override fun getForegroundNotification(): Notification {
        val notificationBuilder =
            NotificationCompat.Builder(context, notificationChannelCreator.getMuteChannelId())
                .setSmallIcon(R.drawable.ic_cached_white_24dp)
                .setContentTitle(null)
        return notificationBuilder.build()
    }

}