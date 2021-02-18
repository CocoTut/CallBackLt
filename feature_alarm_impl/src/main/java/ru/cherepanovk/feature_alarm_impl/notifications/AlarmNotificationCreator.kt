package ru.cherepanovk.feature_alarm_impl.notifications

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.NotificationCompat
import ru.cherepanovk.feature_alarm_impl.*
import ru.cherepanovk.feature_alarm_impl.callservices.AlarmNotificationServiceLauncherImpl.Companion.STOP_FOREGROUND_ACTION
import ru.cherepanovk.feature_alarm_impl.notifications.NotificationParams.Companion.NOTIFICATION_ID_DEFAULT


class AlarmNotificationCreator private constructor(
    private val context: Context,
    private val builder: Builder
) {

    fun createNotification() {
        val notification = getNotification()
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(builder.notificationId, notification)
    }


    fun getNotification(): Notification {
        val icon =
            if (builder.reminderId == null) R.drawable.ic_alarm_add else R.drawable.ic_ring_volume
        val notificationBuilder =
            NotificationCompat.Builder(context, context.getString(R.string.channel_id))
                .setSmallIcon(icon)
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

        builder.reschedulePendingIntent?.let {
            notificationBuilder.addAction(
                R.drawable.ic_snooze_black_24dp,
                context.getString(R.string.btn_notification_reschedule),
                it
            )
        }

        builder.stopAlarmPendingIntent?.let {
            notificationBuilder.addAction(
                R.drawable.ic_snooze_black_24dp,
                context.getString(R.string.stop_foreground),
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
        var reschedulePendingIntent: PendingIntent? = null
            private set
        var openReminderPendingIntent: PendingIntent? = null
            private set
        var stopAlarmPendingIntent: PendingIntent? = null
            private set
        var ringtoneUri: Uri? = null
            private set
        var message: String? = null
            private set
        var reminderId: String? = null
            private set

        var notificationId = 0


        fun addCallAction(params: NotificationParams) = apply {
            params.reminderId?.let {
                val action = context.resources.getString(R.string.action_call)
                callPendingIntent = getPendingIntent(
                    getIntent(params, action)
                )
            }

        }

        fun addRescheduleAction(params: NotificationParams) = apply {
            params.reminderId?.let {
                val action = context.resources.getString(R.string.action_reschedule)
                reschedulePendingIntent = getPendingIntent(
                    getIntent(params, action)
                )
            }
        }


        fun addOpenReminderAction(params: NotificationParams) = apply {
            val action = context.resources.getString(R.string.action_open_reminder)
            openReminderPendingIntent = getPendingIntent(
                getIntent(params, action)
            )
        }

        fun addStopAlarmAction(params: NotificationParams) = apply {
            stopAlarmPendingIntent = getPendingIntent(
                getIntent(params, STOP_FOREGROUND_ACTION)
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

        fun build(): AlarmNotificationCreator {
            return AlarmNotificationCreator(context, this)
        }


        private fun getIntent(
            params: NotificationParams,
            pendingAction: String
        ): Intent {
            this.reminderId = params.reminderId
            val reminderId = params.reminderId
            val phoneNumber = params.phoneNumber
            val notificationId = when {
                reminderId != null -> getNotificationId(reminderId)
                phoneNumber != null -> getNotificationId(phoneNumber)
                else -> NOTIFICATION_ID_DEFAULT
            }

            val actionIntent = Intent(context, NotificationAlarmService::class.java).apply {
                action = pendingAction
            }

            actionIntent.apply {
                putExtras(
                    NotificationParams(
                        phoneNumber,
                        reminderId,
                        notificationId,
                        contactName = params.contactName,
                        description = params.description
                    ).toBundle()
                )
            }



            return actionIntent

        }




        private fun getPendingIntent(intent: Intent): PendingIntent {
            return PendingIntent.getService(
                context,
                NotificationAlarmService.STOP_CALL_NOTIFICATION_SERVICE_FOREGROUND_REQUEST_CODE,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT
            )

        }

        fun getPendingEventForAction(
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
                RESCHEDULE_CODE -> PendingIntent.getActivity(
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

    companion object {
        private const val CALL_REQUEST_CODE = 1
        private const val OPEN_REMINDER_CODE = 0
        private const val RESCHEDULE_CODE = 2
    }
}