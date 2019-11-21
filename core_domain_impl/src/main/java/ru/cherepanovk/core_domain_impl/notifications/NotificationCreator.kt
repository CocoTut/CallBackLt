package ru.cherepanovk.core_domain_impl.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import ru.cherepanovk.core_domain_impl.*

private const val CALL_REQUEST_CODE = 1
private const val OPEN_REMINDER_CODE = 0

class NotificationCreator private constructor(
    private val context: Context,
    private val builder: Builder
) {

    fun createNotification() {
        val notification = getNotification()
        val notificationManager =  context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(builder.notificationId, notification)
    }



    private fun getNotification(): Notification {
        val notificationBuilder = NotificationCompat.Builder(context, context.getString(R.string.channel_id))
            .setSmallIcon(R.drawable.ic_ring_volume)
            .setAutoCancel(true)

        builder.message?.let {
            notificationBuilder
                .setContentText(it)
                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText(it)
                )
        }
        builder.ringtoneUri?.let {
            notificationBuilder.setSound(it)
        }

        builder.callPendingIntent?.let {
            notificationBuilder.addAction(
                R.drawable.ic_call,
                context.getString(R.string.btn_notification_call),
                it
            )
        }

        builder.openReminderPendingIntent?.let {
            notificationBuilder.setContentIntent(it)
        }

        return notificationBuilder.build()
    }


    class Builder(private val context: Context) {
        var callPendingIntent: PendingIntent? = null
            private set
        var openReminderPendingIntent: PendingIntent? = null
            private set
        var ringtoneUri: Uri? = null
            private set
        var message: String? = null
            private set
        var notificationId = 0


        fun addCallAction(reminderIntent: Intent) = apply {
            val action = context.resources.getString(R.string.action_call)
            callPendingIntent = getPendingEventForAction(
                getActionIntent(reminderIntent, action, false),
                CALL_REQUEST_CODE
            )
        }


        fun addOpenReminderAction(reminderIntent: Intent) = apply {
            val action = context.resources.getString(R.string.action_open_reminder)
            openReminderPendingIntent = getPendingEventForAction(
                getActionIntent(reminderIntent, action, true),
                OPEN_REMINDER_CODE
            )
        }

        fun setMessage(reminderIntent: Intent) = apply {
            val contactName = reminderIntent.getStringExtra(CONTACT_NAME)
            val description = reminderIntent.getStringExtra(DESCRIPTION)
            message = buildString {
                append(contactName)
                if (description.isNotBlank()) {
                    append(", ")
                    append(description)
                }

            }
        }

        fun build(): NotificationCreator {
            return NotificationCreator(context, this)
        }


        private fun getActionIntent(
            reminderIntent: Intent,
            action: String,
            openActivity: Boolean
        ): Intent {
            val reminderId = reminderIntent.getStringExtra(REMINDER_ID)
            val phoneNumber = reminderIntent.getStringExtra(PHONE_NUMBER)
            val notificationId = getNotificationId(reminderId)

            return Intent(action).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                if (openActivity)
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)

                putExtra(REMINDER_ID, reminderId)
                putExtra(PHONE_NUMBER, phoneNumber)
                putExtra(NOTIFICATION_ID, notificationId)
            }
        }

        private fun getPendingEventForAction(
            actionIntent: Intent,
            actionRequestCode: Int
        ): PendingIntent {
            return PendingIntent.getActivity(
                context,
                actionRequestCode + actionIntent.getIntExtra(NOTIFICATION_ID, 0),
                actionIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }

        private fun getNotificationId(reminderId: String): Int {
            notificationId = reminderId.hashCode()
            return notificationId
        }

    }
}