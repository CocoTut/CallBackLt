package com.cherepanovky.callbackit.receivers

import android.content.*
import android.telephony.TelephonyManager
import com.cherepanovky.callbackit.receivers.di.DaggerCallStateComponent
import ru.cherepanovk.core.di.ComponentManager
import ru.cherepanovk.core.di.getOrThrow
import ru.cherepanovk.core_db_impl.di.DaggerCoreDbComponent
import ru.cherepanovk.core_domain_api.data.CallListenerHandler
import ru.cherepanovk.core_domain_impl.callservices.di.DaggerCallServicesComponent
import ru.cherepanovk.core_domain_impl.di.DaggerCoreDomainComponent
import javax.inject.Inject

class CallStateReceiver : BroadcastReceiver() {

    @Inject
    lateinit var callListenerHandler: CallListenerHandler

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == TelephonyManager.ACTION_PHONE_STATE_CHANGED) {
            DaggerCallStateComponent.builder()
                .coreDomainApi(
                    DaggerCoreDomainComponent.builder()
                        .contextProvider(ComponentManager.getOrThrow())
                        .build()
                )
                .build()

                .injectCallStateReceiver(this)

            callListenerHandler.startCallLister(intent)
        }
    }


}
