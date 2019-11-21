package ru.cherepanovk.core_domain_api.data

import android.net.Uri

interface AlarmApi {

    fun createAlarm(alarmReminder: AlarmReminder)
}