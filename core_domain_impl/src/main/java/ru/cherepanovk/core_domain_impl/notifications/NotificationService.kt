package ru.cherepanovk.core_domain_impl.notifications

import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService
import ru.cherepanovk.core.exception.CallBackItException
import ru.cherepanovk.core_domain_impl.NOTIFICATION_PARAMS


private const val JOB_ID = 1001
private const val PICKED_RINGTONE = "picked_ringtone"

class NotificationService : JobIntentService() {

    override fun onHandleWork(intent: Intent) {
        val params = NotificationParams.fromBundle(intent.extras)
            ?: throw CallBackItException.CreateNotificationException

        val notificationCreator = NotificationCreator.Builder(this)
            .addCallAction(params)
            .addOpenReminderAction(params)
            .setMessage(params)
            .build()
        notificationCreator.createNotification()
    }

    companion object {
        fun enqueueWork(context: Context, intent: Intent) {
            enqueueWork(context, NotificationService::class.java, JOB_ID, intent)
        }
    }


}