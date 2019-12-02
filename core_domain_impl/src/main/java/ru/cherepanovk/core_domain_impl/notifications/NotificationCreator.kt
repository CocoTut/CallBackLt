package ru.cherepanovk.core_domain_impl.notifications

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.NotificationCompat
import ru.cherepanovk.core_domain_impl.*
import ru.cherepanovk.core_domain_impl.notifications.NotificationParams.Companion.NOTIFICATION_ID_DEFAULT

private const val CALL_REQUEST_CODE = 1
private const val OPEN_REMINDER_CODE = 0

class NotificationCreator private constructor(
    private val context: Context,
    private val builder: Builder
) {

    fun createNotification() {
        val notification = getNotification()
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(builder.notificationId, notification)
    }


    private fun getNotification(): Notification {
        val notificationBuilder =
            NotificationCompat.Builder(context, context.getString(R.string.channel_id))
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
                context.getString(R.string.btn_notification_cal),
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


        fun addCallAction(params: NotificationParams) = apply {
            val action = context.resources.getString(R.string.action_call)
            callPendingIntent = getPendingEventForAction(
                getActionIntent(params, action, false),
                CALL_REQUEST_CODE
            )
        }


        fun addOpenReminderAction(params: NotificationParams) = apply {
            val action = context.resources.getString(R.string.action_open_reminder)
            openReminderPendingIntent = getPendingEventForAction(
                getActionIntent(params, action, true),
                OPEN_REMINDER_CODE
            )
        }

        fun setMessage(params: NotificationParams) = apply {
            val contactName = params.contactName
            val description = params.description
            message = buildString {
                append(contactName)
                if (!description.isNullOrEmpty()) {
                    append(", ")
                    append(description)
                }

            }
        }

        fun build(): NotificationCreator {
            return NotificationCreator(context, this)
        }


        private fun getActionIntent(
            params: NotificationParams,
            action: String,
            openActivity: Boolean
        ): Intent {
            val reminderId = params.reminderId
            val phoneNumber = params.phoneNumber
            val notificationId = reminderId?.let { getNotificationId(reminderId) } ?: NOTIFICATION_ID_DEFAULT

            val actionIntent = when (openActivity) {
                true -> Intent(action)
                false -> Intent(context, NotificationActionsReceiver::class.java)
            }

            actionIntent.apply {
                putExtra(NOTIFICATION_PARAMS, NotificationParams(
                    phoneNumber,
                    reminderId,
                    notificationId,
                    contactName = params.contactName,
                    description = params.description
                ).toBundle())
            }



            return actionIntent

        }

        private fun getPendingEventForAction(
            actionIntent: Intent,
            actionRequestCode: Int
        ): PendingIntent {
            return when (actionRequestCode) {
                CALL_REQUEST_CODE -> PendingIntent.getBroadcast(
                    context,
                    actionRequestCode + notificationId,
                    actionIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
                else ->
                    PendingIntent.getActivity(
                        context,
                        OPEN_REMINDER_CODE + notificationId,
                        actionIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                    )
            }
        }

        private fun getNotificationId(reminderId: String): Int {
            notificationId = reminderId.hashCode()
            return notificationId
        }

    }
}