package ru.cherepanovk.feature_alarm_impl.callservices

import android.content.Context
import android.content.Intent
import android.os.Build
import ru.cherepanovk.feature_alarm_api.data.CallListenerHandler
import timber.log.Timber
import javax.inject.Inject


class CallListenerHandlerImpl @Inject constructor(
    private val context: Context
) : CallListenerHandler {
    override fun startCallLister(phoneStateIntent: Intent) {
        val intent = Intent(context.applicationContext, CallNotificationService::class.java).apply {
            phoneStateIntent.extras?.let {
                putExtras(it)
            } ?: Timber.e(NoSuchElementException("No phone number extras"))

        }
//        context.startService(intent)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent)
        } else {
            context.startService(intent)
        }
    }

}