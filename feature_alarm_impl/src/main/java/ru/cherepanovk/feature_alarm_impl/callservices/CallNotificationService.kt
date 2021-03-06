package ru.cherepanovk.feature_alarm_impl.callservices

import android.Manifest
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.os.IBinder
import android.telephony.TelephonyManager
import androidx.core.content.ContextCompat
import kotlinx.coroutines.*
import ru.cherepanovk.core.di.DI
import ru.cherepanovk.core.platform.ContactPicker
import ru.cherepanovk.core_db_api.data.RemindersDbApi
import ru.cherepanovk.core_preferences_api.data.PreferencesApi
import ru.cherepanovk.feature_alarm_api.di.FeatureAlarmApi
import ru.cherepanovk.feature_alarm_impl.di.FeatureAlarmComponent
import ru.cherepanovk.feature_alarm_impl.notifications.CallListenerNotificationCreator
import ru.cherepanovk.feature_alarm_impl.notifications.NotificationCreator
import ru.cherepanovk.feature_alarm_impl.notifications.NotificationParams
import javax.inject.Inject


class CallNotificationService : Service(), CoroutineScope by CoroutineScope(Dispatchers.Main) {

    @Inject
    lateinit var contactPicker: ContactPicker

    @Inject
    lateinit var remindersDbApi: RemindersDbApi

    @Inject
    lateinit var callListenerNotificationCreator: CallListenerNotificationCreator

    @Inject
    lateinit var preferencesApi: PreferencesApi

    private var incomingCallMade = false
    private var offHookMade = false

    override fun onCreate() {
        super.onCreate()
        DI.getComponent(FeatureAlarmApi::class.java, FeatureAlarmComponent::class.java)
            .getCallServicesComponent()
            .injectCallNotificationService(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForegroundAction()
        if ( hasCallLogPermissions()) {
            if (intent?.action == STOP_FOREGROUND_ACTION) {
                stopSelf()
            } else {
                parseIntent(intent)
            }
        } else {
            stopSelf()
        }


        return super.onStartCommand(intent, flags, startId)
    }

    private fun hasCallLogPermissions(): Boolean =
        ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) ==
                PackageManager.PERMISSION_GRANTED

    private fun parseIntent(intent: Intent?) {
        intent?.extras?.let { extras ->
            extras.getString(TelephonyManager.EXTRA_STATE)?.let { state ->
                if (needShowNotification(state)) {
                    extras.getString(TelephonyManager.EXTRA_INCOMING_NUMBER)?.let { phoneNumber ->
                        preferencesApi.setLastCalledPhoneNumber(phoneNumber)
                        showNotification(phoneNumber)
                    }
                }

            }

        } ?: stopSelf()
    }

    private fun needShowNotification(state: String) =
        when (state) {
            TelephonyManager.EXTRA_STATE_RINGING -> {
                incomingCallMade = true
                false
            }
            TelephonyManager.EXTRA_STATE_OFFHOOK -> {
                offHookMade = true
                false
            }
            TelephonyManager.EXTRA_STATE_IDLE -> checkPreferences()

            else -> {
                stopSelf()
                false
            }
        }

    private fun checkPreferences(): Boolean {
        return when {
            !incomingCallMade && !offHookMade -> {
                stopSelf()
                false
            }
            incomingCallMade && preferencesApi.getTrackingAllIncomingCalls() -> true
            incomingCallMade && !offHookMade && preferencesApi.getTrackingMissedIncomingCalls() -> true
            !incomingCallMade && offHookMade && preferencesApi.getTrackingAllOutgoingCalls() -> true
            else -> {
                stopSelf()
                false
            }
        }


    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    private fun startForegroundAction() {
        val stopIntent = Intent(this, CallNotificationService::class.java).apply {
            action = STOP_FOREGROUND_ACTION
        }
        val stopPending = PendingIntent.getService(
            this,
            STOP_CALL_NOTIFICATION_SERVICE_FOREGROUND_REQUEST_CODE,
            stopIntent,
            PendingIntent.FLAG_CANCEL_CURRENT
        )
        startForeground(
            NOTIFICATION_ID,
            callListenerNotificationCreator.getNotificationWithStop(stopPending)
        )
    }

    override fun onDestroy() {

        cancel()
        super.onDestroy()
        stopForeground(true)
    }


    private fun showNotification(phoneNumber: String) {
        launch {
            val params = getNotificationsParamsFromDb(phoneNumber)
                ?: getNotificationParamsFromContacts(phoneNumber)

            createNotification(params)
            stopSelf()
        }

    }

    private suspend fun getNotificationsParamsFromDb(phoneNumber: String): NotificationParams? {
        return remindersDbApi.getReminderByPhoneNumber(phoneNumber)?.let {
            NotificationParams(
                contactName = it.contactName,
                phoneNumber = it.phoneNumber,
                reminderId = it.id,
                description = it.description
            )
        }
    }

    private fun getNotificationParamsFromContacts(phoneNumber: String): NotificationParams {
        val contactName = contactPicker.getContactNameByPhoneNumber(phoneNumber)
        return NotificationParams(
            contactName = contactName ?: phoneNumber,
            phoneNumber = phoneNumber
        )
    }

    private fun createNotification(params: NotificationParams) {
        NotificationCreator.Builder(this@CallNotificationService)
            .addCallAction(params)
            .addOpenReminderAction(params)
            .addRescheduleAction(params)
            .setMessage(params)
            .build().run {
                createNotification()
            }
    }

    private companion object {
        private const val NOTIFICATION_ID = 22091983
        private const val STOP_CALL_NOTIFICATION_SERVICE_FOREGROUND_REQUEST_CODE = 1403021987
        private const val STOP_FOREGROUND_ACTION = "STOP_CALL_NOTIFICATION_SERVICE_FOREGROUND_ACTION"
    }
}