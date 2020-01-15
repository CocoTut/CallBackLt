package ru.cherepanovk.core_domain_impl.callservices

import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import ru.cherepanovk.core.di.ComponentManager
import ru.cherepanovk.core.di.getOrThrow
import ru.cherepanovk.core_domain_impl.callservices.di.DaggerCallServicesComponent
import ru.cherepanovk.core_domain_impl.notifications.CallListenerNotificationCreator
import javax.inject.Inject


class CallListenerService : Service() {

    private val telephonyManager: TelephonyManager by lazy {
        getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    }
    @Inject
    lateinit var callListenerNotificationCreator: CallListenerNotificationCreator

    override fun onCreate() {
        super.onCreate()
        DaggerCallServicesComponent.builder()
            .contextProvider(ComponentManager.getOrThrow())
            .build()
            .injectCallListenerJobService(this)
    }


    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            START_FOREGROUND_ACTION -> {
                startForegroundAction()
                startCallListen()
            }
            STOP_FOREGROUND_ACTION -> stopForegroundAction()
        }
        return super.onStartCommand(intent, flags, startId)
    }


    private fun stopForegroundAction() {
        stopForeground(true)
    }

    private fun startForegroundAction() {


        val stopIntent = Intent(this, CallListenerService::class.java).apply {
            action = STOP_FOREGROUND_ACTION
        }
        val stopPending = PendingIntent.getService(
            this,
            STOP_REQUEST_CODE,
            stopIntent,
            PendingIntent.FLAG_CANCEL_CURRENT
        )

        startForeground(
            NOTIFICATION_ID,
            callListenerNotificationCreator.getNotification(stopPending)
        )
    }

    private fun startCallListen() {

        val listener = CallPhoneStateListener(this)
        telephonyManager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE)
    }


    companion object {
        const val STOP_FOREGROUND_ACTION = "STOP_FOREGROUND_ACTION"
        const val START_FOREGROUND_ACTION = "START_FOREGROUND_ACTION"
        private const val NOTIFICATION_ID = 22091983
        private const val STOP_REQUEST_CODE = 321987

    }
}