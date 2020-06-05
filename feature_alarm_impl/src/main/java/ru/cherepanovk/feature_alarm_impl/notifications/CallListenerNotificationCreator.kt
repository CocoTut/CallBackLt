package ru.cherepanovk.feature_alarm_impl.notifications

import android.app.Notification
import android.app.PendingIntent

interface CallListenerNotificationCreator {
    fun getNotificationWithStop(stopPendingIntent: PendingIntent): Notification
    fun getForegroundNotification(): Notification
}