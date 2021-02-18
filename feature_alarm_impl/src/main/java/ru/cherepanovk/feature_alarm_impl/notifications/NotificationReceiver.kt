package ru.cherepanovk.feature_alarm_impl.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import ru.cherepanovk.core.di.ComponentManager
import ru.cherepanovk.core.di.getOrThrow
import ru.cherepanovk.core_preferences_api.data.PreferencesApi
import ru.cherepanovk.feature_alarm_impl.callservices.di.DaggerNotificationAlarmServiceComponent
import javax.inject.Inject

class NotificationReceiver : BroadcastReceiver() {
    @Inject
    lateinit var preferencesApi: PreferencesApi

    override fun onReceive(context: Context, intent: Intent) {
        DaggerNotificationAlarmServiceComponent.builder()
            .contextProvider(ComponentManager.getOrThrow())
            .corePreferencesApi(ComponentManager.getOrThrow())
            .build()
            .injectNotificationReceiver(this)

        if (preferencesApi.isLongAlarmEnable().not()) {
            showNotification(context,intent)
        } else {
            intent.extras?.let {
                startCallLister(context, it)
            }
        }
    }

    private fun showNotification(context: Context, intent: Intent) {
        NotificationParams.fromBundle(intent.extras)?.let {
            NotificationCreator.Builder(context)
                .addCallAction(it)
                .addRescheduleAction(it)
                .addOpenReminderAction(it)
                .setMessage(it)
                .build()
                .run {
                    createNotification()
                }
        }
    }

    private fun startCallLister(context: Context, extras: Bundle) {
        val intent = Intent(context.applicationContext, NotificationAlarmService::class.java).apply {
            putExtras(extras)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent)
        } else {
            context.startService(intent)
        }
    }
}
