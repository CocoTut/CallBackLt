package com.cherepanovky.callbackit.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import com.cherepanovky.callbackit.receivers.di.DaggerCallStateComponent
import ru.cherepanovk.core.di.DI
import ru.cherepanovk.feature_alarm_api.data.CallListenerHandler
import ru.cherepanovk.feature_alarm_api.di.FeatureAlarmApi
import javax.inject.Inject

class CallStateReceiver : BroadcastReceiver() {

    @Inject
    lateinit var callListenerHandler: CallListenerHandler

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == TelephonyManager.ACTION_PHONE_STATE_CHANGED) {
            DaggerCallStateComponent.builder()
                .featureAlarmApi(DI.getDependency(FeatureAlarmApi::class.java))
                .build()
                .injectCallStateReceiver(this)
            callListenerHandler.startCallLister(intent)
        }
    }


}
