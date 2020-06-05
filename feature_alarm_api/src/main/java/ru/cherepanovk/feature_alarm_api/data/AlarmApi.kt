package ru.cherepanovk.feature_alarm_api.data

interface AlarmApi {

    fun createAlarm(alarmReminder: AlarmReminder)

    fun cancelAlarm(alarmReminder: AlarmReminder)
}