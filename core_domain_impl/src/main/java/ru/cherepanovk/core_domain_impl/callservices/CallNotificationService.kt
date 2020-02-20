package ru.cherepanovk.core_domain_impl.callservices

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.telephony.TelephonyManager
import android.widget.Toast
import kotlinx.coroutines.*
import ru.cherepanovk.core.di.ComponentManager
import ru.cherepanovk.core.di.getOrThrow
import ru.cherepanovk.core.platform.ContactPicker
import ru.cherepanovk.core_db_api.data.DbApi
import ru.cherepanovk.core_db_impl.di.DaggerCoreDbComponent
import ru.cherepanovk.core_domain_impl.callservices.di.DaggerCallServicesComponent
import ru.cherepanovk.core_domain_impl.notifications.CallListenerNotificationCreator
import ru.cherepanovk.core_domain_impl.notifications.NotificationCreator
import ru.cherepanovk.core_domain_impl.notifications.NotificationParams
import javax.inject.Inject

private const val NOTIFICATION_ID = 22091983

class CallNotificationService : Service(), CoroutineScope by CoroutineScope(Dispatchers.Main) {

    @Inject
    lateinit var contactPicker: ContactPicker

    @Inject
    lateinit var dbApi: DbApi

    @Inject
    lateinit var callListenerNotificationCreator: CallListenerNotificationCreator

    override fun onCreate() {
        super.onCreate()
        DaggerCallServicesComponent.builder()
            .contextProvider(ComponentManager.getOrThrow())
            .coreDbApi(
                DaggerCoreDbComponent.builder()
                    .contextProvider(ComponentManager.getOrThrow())
                    .build()
            )
            .build()
            .injectCallNotificationService(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForegroundAction()
        parseIntent(intent)
        return super.onStartCommand(intent, flags, startId)
    }

    private fun parseIntent(intent: Intent?) {
        intent?.extras?.let { extras ->
            extras.getString(TelephonyManager.EXTRA_STATE)?.let { state ->
                if (state != TelephonyManager.EXTRA_STATE_IDLE)
                    extras.getString(TelephonyManager.EXTRA_INCOMING_NUMBER)?.let { phoneNumber ->
                        showNotification(phoneNumber)
                    }
                else stopSelf()
            }

        } ?: stopSelf()
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    private fun startForegroundAction() {
        startForeground(
            NOTIFICATION_ID,
            callListenerNotificationCreator.getForegroundNotification()
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
        return dbApi.getReminderByPhoneNumber(phoneNumber)?.let {
            NotificationParams(
                contactName = it.contactName(),
                phoneNumber = it.phoneNumber(),
                reminderId = it.id(),
                description = it.description()
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
        val notificationCreator = NotificationCreator.Builder(this@CallNotificationService)
            .addCallAction(params)
            .addOpenReminderAction(params)
            .setMessage(params)
            .build()
        notificationCreator.createNotification()
    }


}