package ru.cherepanovk.feature_alarm_impl.alarm

import android.content.Context
import android.content.Intent
import android.os.Bundle
import ru.cherepanovk.feature_alarm_impl.notifications.NotificationActionsReceiver
import javax.inject.Inject

class AlarmPendingIntentProvider @Inject constructor() {
    fun getActionIntent(
        params: Bundle,
        action: String,
        openActivity: Boolean,
        context: Context
    ): Intent {

        val actionIntent = when (openActivity) {
            true -> Intent(action)
            false -> Intent(context, NotificationActionsReceiver::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setAction(action)
            }
        }

        actionIntent.apply {
            putExtras(
                params
            )
        }



        return actionIntent

    }

}