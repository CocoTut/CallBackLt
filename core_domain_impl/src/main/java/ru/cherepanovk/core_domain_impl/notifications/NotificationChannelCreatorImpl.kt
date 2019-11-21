package ru.cherepanovk.core_domain_impl.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import ru.cherepanovk.core_domain_api.data.NotificationChannelCreator
import ru.cherepanovk.core_domain_impl.R
import javax.inject.Inject

class NotificationChannelCreatorImpl @Inject constructor(
   private val context: Context
): NotificationChannelCreator {
    override fun createDefaultNotificationChannel(ringtoneUri: Uri?) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val att = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()

            val ringtone = ringtoneUri ?: RingtoneManager.getDefaultUri(
                RingtoneManager.TYPE_NOTIFICATION)

            val channelDefault = NotificationChannel(context.getString(R.string.channel_id),
                context.getString(R.string.channel_id),
                NotificationManager.IMPORTANCE_DEFAULT).apply {
                setSound(ringtone, att)
            }

            notificationManager.createNotificationChannel(channelDefault)
        }
    }
}