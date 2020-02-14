package ru.cherepanovk.core_domain_impl.callservices.di

import dagger.Component
import ru.cherepanovk.core.di.dependencies.ContextProvider
import ru.cherepanovk.core_domain_impl.callservices.CallListenerService
import ru.cherepanovk.core_domain_impl.callservices.CallNotificationService

@Component(
    dependencies = [
        ContextProvider::class],
    modules = [CallServicesModule::class]
    )
interface CallServicesComponent {
    fun injectCallListenerJobService(callListenerService: CallListenerService)

    fun injectCallNotificationService(callNotificationService: CallNotificationService)
}