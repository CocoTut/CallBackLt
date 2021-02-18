package ru.cherepanovk.feature_alarm_api.data

import android.content.Intent

interface AlarmNotificationServiceLauncher {
    fun stopAlarmService():Intent
    fun stopAlarm():Intent
}