package ru.cherepanovk.core_domain_impl

import android.app.AlarmManager
import ru.cherepanovk.core_domain_api.data.AlarmApi
import ru.cherepanovk.core_domain_api.data.AlarmReminder
import javax.inject.Inject

class CallBackAlarm @Inject constructor(private val alarmManager: AlarmManager) : AlarmApi {

    override fun createAlarm(alarmReminder: AlarmReminder) {

    }
}