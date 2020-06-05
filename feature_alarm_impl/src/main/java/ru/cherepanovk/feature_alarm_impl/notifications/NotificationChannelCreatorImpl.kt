package ru.cherepanovk.feature_alarm_impl.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import ru.cherepanovk.feature_alarm_api.data.NotificationChannelCreator
import ru.cherepanovk.feature_alarm_impl.R
import javax.inject.Inject

class NotificationChannelCreatorImpl @Inject constructor(
    private val context: Context
) : NotificationChannelCreator {
    private val notificationManager: NotificationManager by lazy {
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }
    private val channelDefaultId: String
        get() = context.getString(R.string.channel_id)

    private val channelMuteId: String
        get() = context.getString(R.string.mute_channel_id)


    override fun createDefaultNotificationChannel(ringtoneUri: Uri?) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            if (isChannelCreated(channelDefaultId) && ringtoneUri == null)
                return

            val att = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()

            val ringtone = ringtoneUri ?: RingtoneManager.getDefaultUri(
                RingtoneManager.TYPE_NOTIFICATION
            )

            val channelDefault = NotificationChannel(
                channelDefaultId,
                channelDefaultId,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                setSound(ringtone, att)
            }

            notificationManager.createNotificationChannel(channelDefault)
        }
    }

    override fun createMuteNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (isChannelCreated(channelMuteId))
                return
            val channelMute = NotificationChannel(
                channelMuteId,
                channelMuteId,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                setSound(null, null)

            }

            notificationManager.createNotificationChannel(channelMute)
        }
    }

    override fun getDefaultChannelId(): String {
        if (!isChannelCreated(channelDefaultId))
            createDefaultNotificationChannel(null)
        return channelDefaultId
    }

    override fun getMuteChannelId(): String {
        if (!isChannelCreated(channelMuteId))
            createMuteNotificationChannel()
        return channelMuteId
    }

    private fun isChannelCreated(channelId: String): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            notificationManager.getNotificationChannel(channelId) != null
        else true
    }
}