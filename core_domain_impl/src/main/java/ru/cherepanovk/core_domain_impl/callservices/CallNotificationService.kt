package ru.cherepanovk.core_domain_impl.callservices

import android.app.IntentService
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.widget.Toast
import androidx.core.app.JobIntentService

class CallNotificationService : Service() {

    fun onHandleIntent(intent: Intent) {
        val extras = intent.extras

    }

    override fun onCreate() {
        super.onCreate()

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(this, "Call!", Toast.LENGTH_SHORT).show()
        return super.onStartCommand(intent, flags, startId)
    }

    companion object {
        private const val SERVICE_NAME = "CallNotification"

    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }


}