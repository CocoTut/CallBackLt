package ru.cherepanovk.feature_alarm_impl

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import ru.cherepanovk.core.di.DI
import ru.cherepanovk.core_db_api.data.RemindersDbApi
import ru.cherepanovk.feature_alarm_api.data.AlarmApi
import ru.cherepanovk.feature_alarm_api.data.AlarmReminderModel
import ru.cherepanovk.feature_alarm_api.di.FeatureAlarmApi
import ru.cherepanovk.feature_alarm_impl.di.FeatureAlarmComponent
import java.util.*
import javax.inject.Inject

class BootCompleteReceiver : BroadcastReceiver(), CoroutineScope by CoroutineScope(Dispatchers.IO) {

    @Inject
    lateinit var remindersDbApi: RemindersDbApi

    @Inject
    lateinit var alarmApi: AlarmApi

    override fun onReceive(context: Context, intent: Intent) {
        DI.getComponent(FeatureAlarmApi::class.java, FeatureAlarmComponent::class.java)
            .inject(this)
        recreateRemindersAfterReboot(goAsync())
    }

    private fun recreateRemindersAfterReboot(result: PendingResult) {
        launch {
            remindersDbApi.getRemindersAfterDate(Date())
                .map { reminder ->
                    AlarmReminderModel(
                        id = reminder.id,
                        phoneNumber = reminder.phoneNumber,
                        description = reminder.description,
                        contactName = reminder.contactName,
                        dateTimeEvent = reminder.dateTimeEvent
                    )
                }.forEach {
                    alarmApi.createAlarm(it)
                }
            result.finish()
            cancel()
        }
    }
}
