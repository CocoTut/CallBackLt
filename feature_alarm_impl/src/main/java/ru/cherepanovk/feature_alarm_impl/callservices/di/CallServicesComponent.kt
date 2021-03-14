package ru.cherepanovk.feature_alarm_impl.callservices.di

import dagger.Subcomponent
import ru.cherepanovk.feature_alarm_impl.callservices.CallListenerService
import ru.cherepanovk.feature_alarm_impl.callservices.CallNotificationService

@Subcomponent(modules = [CallServicesModule::class])
interface CallServicesComponent {
    fun injectCallListenerJobService(callListenerService: CallListenerService)

    fun injectCallNotificationService(callNotificationService: CallNotificationService)
}