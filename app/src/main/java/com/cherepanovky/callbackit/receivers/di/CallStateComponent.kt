package com.cherepanovky.callbackit.receivers.di

import com.cherepanovky.callbackit.receivers.CallStateReceiver
import dagger.Component
import ru.cherepanovk.core_preferences_api.di.CorePreferencesApi
import ru.cherepanovk.feature_alarm_api.di.FeatureAlarmApi

@Component(
    dependencies = [
        FeatureAlarmApi::class
    ]
)
interface CallStateComponent {

    fun injectCallStateReceiver(callStateReceiver: CallStateReceiver)
}