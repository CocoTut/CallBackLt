package ru.cherepanovk.core_domain_impl.callservices

import android.content.Context
import android.content.Intent
import ru.cherepanovk.core_domain_api.data.CallListenerHadler
import javax.inject.Inject

class CallListenerHadlerImpl @Inject constructor(
    private val context: Context
) : CallListenerHadler {
    override fun startCallLister() {
        val intent = Intent(context, CallListenerService::class.java).apply {
            action = CallListenerService.START_FOREGROUND_ACTION
        }
        context.startService(intent)
    }
}