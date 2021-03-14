package ru.cherepanovk.feature_alarm_impl.callservices

import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import ru.cherepanovk.core.di.DI
import ru.cherepanovk.feature_alarm_api.di.FeatureAlarmApi
import ru.cherepanovk.feature_alarm_impl.di.FeatureAlarmComponent
import ru.cherepanovk.feature_alarm_impl.notifications.CallListenerNotificationCreator
import javax.inject.Inject


class CallListenerService : Service() {

    private val listener = CallPhoneStateListener()
    private val telephonyManager: TelephonyManager by lazy { getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager }

    @Inject
    lateinit var callListenerNotificationCreator: CallListenerNotificationCreator

    override fun onCreate() {
        super.onCreate()
        DI.getComponent(FeatureAlarmApi::class.java, FeatureAlarmComponent::class.java)
            .getCallServicesComponent()
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
        telephonyManager.listen(listener, PhoneStateListener.LISTEN_NONE)

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
            callListenerNotificationCreator.getNotificationWithStop(stopPending)
        )


    }

    private fun startCallListen() {

        telephonyManager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE)
    }


    companion object {
        const val STOP_FOREGROUND_ACTION = "STOP_FOREGROUND_ACTION"
        const val START_FOREGROUND_ACTION = "START_FOREGROUND_ACTION"
        private const val NOTIFICATION_ID = 22091983
        private const val STOP_REQUEST_CODE = 321987

    }
}