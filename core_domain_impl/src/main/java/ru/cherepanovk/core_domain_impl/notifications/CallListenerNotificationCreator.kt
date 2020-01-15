package ru.cherepanovk.core_domain_impl.notifications

import android.app.Notification
import android.app.PendingIntent

interface CallListenerNotificationCreator {
    fun getNotification(stopPendingIntent: PendingIntent): Notification
}