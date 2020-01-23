package ru.cherepanovk.core_domain_impl.callservices

import android.app.PendingIntent
import android.app.Service
import android.content.ContentProvider
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import kotlinx.coroutines.*
import ru.cherepanovk.core.di.ComponentManager
import ru.cherepanovk.core.di.dependencies.ContextProvider
import ru.cherepanovk.core.di.getOrThrow
import ru.cherepanovk.core_domain_impl.callservices.di.DaggerCallServicesComponent
import ru.cherepanovk.core_domain_impl.notifications.CallListenerNotificationCreator
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


class CallListenerService : Service(), CoroutineScope by CoroutineScope(Dispatchers.IO), ContextProvider {

    private val listener = CallPhoneStateListener()
    private val telephonyManager: TelephonyManager by lazy {  getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager }

    @Inject
    lateinit var callListenerNotificationCreator: CallListenerNotificationCreator

    override fun onCreate() {

        super.onCreate()
        println("Boot is completed1! CallListenerService is created")
        println("bindonServiceDisconnected")
        DaggerCallServicesComponent.builder()
            .contextProvider(this)
            .build()
            .injectCallListenerJobService(this)

        launch {
            startCallListen()
        }
    }


    override fun onBind(p0: Intent?): IBinder? {

        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        println("Boot is completed1! CallListenerService is onStartCommand")
        when (intent?.action) {
            START_FOREGROUND_ACTION -> {
                startForegroundAction()
                launch {
                    startCallListen()
                }
            }
            STOP_FOREGROUND_ACTION -> stopForegroundAction()
        }
        return super.onStartCommand(intent, flags, startId)
    }


    private fun stopForegroundAction() {
        telephonyManager.listen(listener, PhoneStateListener.LISTEN_NONE)
        cancel()
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

    private suspend fun startCallListen() {

        telephonyManager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE)
//        while (true) {
//            delay(1000)
//            println("${Thread.currentThread()} coroutines2")
//        }
    }


    companion object {
        const val STOP_FOREGROUND_ACTION = "STOP_FOREGROUND_ACTION"
        const val START_FOREGROUND_ACTION = "START_FOREGROUND_ACTION"
        private const val NOTIFICATION_ID = 22091983
        private const val STOP_REQUEST_CODE = 321987

    }

    override fun getContext(): Context {
        return this
    }

}