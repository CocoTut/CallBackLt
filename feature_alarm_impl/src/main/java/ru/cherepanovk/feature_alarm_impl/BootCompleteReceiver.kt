package ru.cherepanovk.feature_alarm_impl

import android.content.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import ru.cherepanovk.core.di.ComponentManager
import ru.cherepanovk.core.di.getOrThrow
import ru.cherepanovk.core_db_api.data.DbApi
import ru.cherepanovk.feature_alarm_api.data.AlarmApi
import ru.cherepanovk.feature_alarm_api.data.AlarmReminderModel
import ru.cherepanovk.feature_alarm_impl.di.DaggerBootRecreateRemindersComponent
import ru.cherepanovk.feature_alarm_impl.di.DaggerFeatureAlarmComponent
import java.util.*
import javax.inject.Inject

class BootCompleteReceiver : BroadcastReceiver(), CoroutineScope by CoroutineScope(Dispatchers.IO) {

    @Inject
    lateinit var dbApi: DbApi

    @Inject
    lateinit var alarmApi: AlarmApi

    override fun onReceive(context: Context, intent: Intent) {
        DaggerBootRecreateRemindersComponent.builder()
            .featureAlarmApi(
                DaggerFeatureAlarmComponent.builder()
                    .contextProvider(ComponentManager.getOrThrow())
                    .build()
            )
            .coreDbApi(ComponentManager.getOrThrow())
            .build()
            .inject(this)
        goAsync().let {
            recreateRemindersAfterReboot(it)
        }
    }

    private fun recreateRemindersAfterReboot(result: PendingResult) {
        launch {
            dbApi.getRemindersAfterDate(Date())
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
