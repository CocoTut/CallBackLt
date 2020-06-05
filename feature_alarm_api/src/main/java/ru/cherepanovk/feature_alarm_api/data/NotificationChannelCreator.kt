package ru.cherepanovk.feature_alarm_api.data

import android.net.Uri

interface NotificationChannelCreator {
    fun createDefaultNotificationChannel(ringtoneUri: Uri?)

    fun createMuteNotificationChannel()

    fun getDefaultChannelId(): String

    fun getMuteChannelId(): String
}