package ru.cherepanovk.core_domain_impl.callservices

import android.app.IntentService
import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService

class CallNotificationService : IntentService(SERVICE_NAME) {

    override fun onHandleIntent(intent: Intent) {
        val extras = intent.extras
    }

    companion object {
        private const val SERVICE_NAME = "CallNotification"

    }


}