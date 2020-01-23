package ru.cherepanovk.core_domain_impl.callservices

import android.content.*
import android.os.Handler
import android.os.IBinder
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.*
import ru.cherepanovk.core_db_api.data.DbApi
import ru.cherepanovk.core_domain_impl.callservices.di.DaggerCallServicesComponent
import ru.cherepanovk.core_domain_impl.notifications.NotificationCreator
import ru.cherepanovk.core_domain_impl.notifications.NotificationParams
import timber.log.Timber
import javax.inject.Inject

class CallStateReceiver : BroadcastReceiver(), CoroutineScope by CoroutineScope(Dispatchers.IO) {

    private lateinit var context: Context

//    @Inject
//    lateinit var dbApi: DbApi

    override fun onReceive(context: Context, intent: Intent) {
        this.context = context

        launch {
            val extras = intent.extras
            extras?.getString(TelephonyManager.EXTRA_STATE)?.let {state ->
                if (state != TelephonyManager.EXTRA_STATE_IDLE)
                    extras.getString(TelephonyManager.EXTRA_INCOMING_NUMBER)?.let {phoneNumer ->
                        showNotification(phoneNumer)
                    }
            }



        }
    }

    private fun showNotification(phoneNumber: String) {
        val params = NotificationParams(contactName = phoneNumber)

        val notificationCreator = NotificationCreator.Builder(context)
            .addCallAction(params)
            .addOpenReminderAction(params)
            .setMessage(params)
            .build()
        notificationCreator.createNotification()
        cancel()
    }


}
