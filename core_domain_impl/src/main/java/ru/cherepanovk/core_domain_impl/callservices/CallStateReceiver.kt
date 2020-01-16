package ru.cherepanovk.core_domain_impl.callservices

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class CallStateReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val serviceIntent = Intent(context, CallNotificationService::class.java).apply {
            action = CallListenerService.START_FOREGROUND_ACTION
            putExtra("extras", intent.extras)
        }
        context.startService(serviceIntent)
        Toast.makeText(context, "Call!", Toast.LENGTH_SHORT).show()

    }
}
