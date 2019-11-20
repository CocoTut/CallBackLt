package ru.cherepanovk.core_domain_impl.notifications

import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService


private const val JOB_ID = 1001
private const val PICKED_RINGTONE = "picked_ringtone"

class NotificationService : JobIntentService() {

    override fun onHandleWork(intent: Intent) {
        val notificationCreator = NotificationCreator.Builder(this)
            .addCallAction(intent)
            .addOpenReminderAction(intent)
            .setMessage(intent)
            .build()
        notificationCreator.createNotification()
    }

    companion object {
        fun enqueueWork(context: Context, intent: Intent) {
            enqueueWork(context, NotificationService::class.java, JOB_ID, intent)
        }
    }


}