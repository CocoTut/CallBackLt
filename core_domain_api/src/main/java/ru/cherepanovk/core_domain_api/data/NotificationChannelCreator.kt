package ru.cherepanovk.core_domain_api.data

import android.net.Uri

interface NotificationChannelCreator {
    fun createDefaultNotificationChannel(ringtoneUri: Uri?)

    fun createMuteNotificationChannel()

    fun getDefaultChannelId(): String

    fun getMuteChannelId(): String
}