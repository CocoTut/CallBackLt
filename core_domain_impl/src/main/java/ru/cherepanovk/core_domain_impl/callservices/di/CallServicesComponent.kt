package ru.cherepanovk.core_domain_impl.callservices.di

import dagger.Component
import ru.cherepanovk.core.di.dependencies.ContextProvider
import ru.cherepanovk.core_domain_impl.BootCompleteReceiver
import ru.cherepanovk.core_domain_impl.callservices.CallListenerService
import ru.cherepanovk.core_domain_impl.callservices.CallStateReceiver
import ru.cherepanovk.core_domain_impl.callservices.CallStateStarterJob

@Component(
    dependencies = [
        ContextProvider::class],
    modules = [CallServicesModule::class]
    )
interface CallServicesComponent {
    fun injectCallListenerJobService(callListenerService: CallListenerService)

    fun injectCallStateReceiver(callStateReceiver: CallStateReceiver)

    fun injectCallStateStarterJob(callStateStarterJob: CallStateStarterJob)

    fun injectBootCompleteReceiver(bootCompleteReceiver: BootCompleteReceiver)
}