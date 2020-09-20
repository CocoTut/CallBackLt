package ru.cherepanovk.feature_alarm_api.data

import android.app.NotificationChannel
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi

interface NotificationChannelCreator {
    fun createDefaultNotificationChannel(ringtoneUri: Uri?)

    fun createMuteNotificationChannel()

    fun getDefaultChannelId(): String

    fun getMuteChannelId(): String

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDefaultChannel(): NotificationChannel?
}