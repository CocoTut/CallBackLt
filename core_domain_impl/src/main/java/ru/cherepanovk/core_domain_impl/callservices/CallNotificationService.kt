package ru.cherepanovk.core_domain_impl.callservices

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.telephony.TelephonyManager
import android.widget.Toast
import ru.cherepanovk.core.di.ComponentManager
import ru.cherepanovk.core.di.getOrThrow
import ru.cherepanovk.core.platform.ContactPicker
import ru.cherepanovk.core_domain_impl.callservices.di.DaggerCallServicesComponent
import ru.cherepanovk.core_domain_impl.notifications.NotificationCreator
import ru.cherepanovk.core_domain_impl.notifications.NotificationParams
import javax.inject.Inject

class CallNotificationService : Service() {

    @Inject
    lateinit var contactPicker: ContactPicker

    override fun onCreate() {
        super.onCreate()
        DaggerCallServicesComponent.builder()
            .contextProvider(ComponentManager.getOrThrow())
            .build()
            .injectCallNotificationService(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.extras?.let { extras ->
           extras.getString(TelephonyManager.EXTRA_STATE)?.let { state ->
               if (state != TelephonyManager.EXTRA_STATE_IDLE)
                   extras.getString(TelephonyManager.EXTRA_INCOMING_NUMBER)?.let { phoneNumber ->
                       showNotification(phoneNumber)
                   }
           }

        }
        return super.onStartCommand(intent, flags, startId)
    }
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }


    private fun showNotification(phoneNumber: String) {
        val contactName = contactPicker.getContactNameByPhoneNumber(phoneNumber)
        val params = NotificationParams(
            contactName = contactName ?:phoneNumber,
            phoneNumber = phoneNumber)

        val notificationCreator = NotificationCreator.Builder(this)
            .addCallAction(params)
            .addOpenReminderAction(params)
            .setMessage(params)
            .build()
        notificationCreator.createNotification()

    }

}