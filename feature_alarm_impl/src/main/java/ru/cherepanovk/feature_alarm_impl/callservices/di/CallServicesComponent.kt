package ru.cherepanovk.feature_alarm_impl.callservices.di

import dagger.Component
import ru.cherepanovk.core.di.dependencies.ContextProvider
import ru.cherepanovk.core_db_api.di.CoreDbApi
import ru.cherepanovk.feature_alarm_impl.callservices.CallListenerService
import ru.cherepanovk.feature_alarm_impl.callservices.CallNotificationService

@Component(
    dependencies = [
        ContextProvider::class,
        CoreDbApi::class
    ],
    modules = [CallServicesModule::class]
    )
interface CallServicesComponent {
    fun injectCallListenerJobService(callListenerService: CallListenerService)

    fun injectCallNotificationService(callNotificationService: CallNotificationService)
}