package ru.cherepanovk.core_domain_impl.callservices

import android.content.Context
import android.content.Intent
import android.os.Build
import ru.cherepanovk.core_domain_api.data.CallListenerHadler
import javax.inject.Inject


class CallListenerHandlerImpl @Inject constructor(
    private val context: Context
) : CallListenerHadler {
    override fun startCallLister() {
        val intent = Intent(context.applicationContext, CallListenerService::class.java).apply {
            action = CallListenerService.START_FOREGROUND_ACTION
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent)
        } else {
            context.startService(intent)
        }
    }
}