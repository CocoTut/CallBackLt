package ru.cherepanovk.feature_alarm_impl.callservices

import android.content.Context
import android.content.Intent
import ru.cherepanovk.feature_alarm_api.data.AlarmNotificationServiceLauncher
import ru.cherepanovk.feature_alarm_impl.notifications.NotificationAlarmService
import javax.inject.Inject

class AlarmNotificationServiceLauncherImpl @Inject constructor(
    private val context: Context
) : AlarmNotificationServiceLauncher {
    override fun stopAlarmService(): Intent =
        Intent(context, NotificationAlarmService::class.java).apply {
            action = STOP_FOREGROUND_ACTION
        }

    override fun stopAlarm(): Intent =
        Intent(context, NotificationAlarmService::class.java).apply {
            action = STOP_PLAY_ALARM
        }


    companion object {
        const val STOP_FOREGROUND_ACTION =
            "STOP_NOTIFICATION_ALARM_SERVICE_FOREGROUND_ACTION"
        const val STOP_PLAY_ALARM =
            "STOP_NOTIFICATION_ALARM_PLAY"
    }
}