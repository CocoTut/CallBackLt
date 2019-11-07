package ru.cherepanovk.core_domain_api.data

interface AlarmApi {

    fun createAlarm(alarmReminder: AlarmReminder)
}