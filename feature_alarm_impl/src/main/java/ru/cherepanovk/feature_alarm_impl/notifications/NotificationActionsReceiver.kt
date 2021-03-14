package ru.cherepanovk.feature_alarm_impl.notifications

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import ru.cherepanovk.core.di.DI
import ru.cherepanovk.core.utils.getDialIntent
import ru.cherepanovk.feature_alarm_api.data.AlarmNotificationServiceLauncher
import ru.cherepanovk.feature_alarm_api.di.FeatureAlarmApi
import ru.cherepanovk.feature_alarm_impl.di.FeatureAlarmComponent
import ru.cherepanovk.feature_alarm_impl.notifications.NotificationParams.Companion.NOTIFICATION_ID_DEFAULT
import javax.inject.Inject

class NotificationActionsReceiver : BroadcastReceiver() {
    @Inject
    lateinit var alarmNotificationServiceLauncher: AlarmNotificationServiceLauncher

    val notificationActionsProvider = NotificationActionProvider()


    override fun onReceive(context: Context, intent: Intent) {
        DI.getComponent(FeatureAlarmApi::class.java, FeatureAlarmComponent::class.java)
            .inject(this)

        val notificationParams = NotificationParams.fromBundle(intent.extras)
        when(intent.action) {
            notificationActionsProvider.getCallAction(context) ->  openDialActivity(context, notificationParams)
            else -> context.sendBroadcast(intent)
        }
        cancelNotification(context, notificationParams)
    }

    private fun cancelNotification(context: Context, params: NotificationParams?) {

        val notificationId = params?.notificationId ?: NOTIFICATION_ID_DEFAULT
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(notificationId)
        if (params?.alarmed == true)
            context.startService(alarmNotificationServiceLauncher.stopAlarmService())
        else
            context.startService(alarmNotificationServiceLauncher.stopAlarm())
    }

    private fun openDialActivity(context: Context, params: NotificationParams?) {
        //close notification panel
        context.sendBroadcast(Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS))

        val phoneNumber = params?.phoneNumber
        context.startActivity(getDialIntent(phoneNumber))

    }
}
