package com.cherepanovky.callbackit.receivers

import android.content.*
import android.telephony.TelephonyManager
import com.cherepanovky.callbackit.receivers.di.DaggerCallStateComponent
import ru.cherepanovk.core.di.ComponentManager
import ru.cherepanovk.core.di.getOrThrow
import ru.cherepanovk.feature_alarm_api.data.CallListenerHandler
import ru.cherepanovk.feature_alarm_impl.di.DaggerFeatureAlarmComponent
import javax.inject.Inject

class CallStateReceiver : BroadcastReceiver() {

    @Inject
    lateinit var callListenerHandler: CallListenerHandler

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == TelephonyManager.ACTION_PHONE_STATE_CHANGED) {
            DaggerCallStateComponent.builder()
                .featureAlarmApi(
                    DaggerFeatureAlarmComponent.builder()
                        .contextProvider(ComponentManager.getOrThrow())
                        .build()
                )
                .build()
                .injectCallStateReceiver(this)
            callListenerHandler.startCallLister(intent)
        }
    }


}
