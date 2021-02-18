package ru.cherepanovk.feature_alarm_impl.notifications

import android.content.Context
import ru.cherepanovk.feature_alarm_impl.R
import javax.inject.Inject

class NotificationActionProvider @Inject constructor() {

    fun getCallAction(context: Context): String =
        context.getString(R.string.action_call)

    fun getRescheduleAction(context: Context): String =
        context.getString(R.string.action_reschedule)

    fun getOpenReminderAction(context: Context): String =
        context.getString(R.string.action_open_reminder)
}