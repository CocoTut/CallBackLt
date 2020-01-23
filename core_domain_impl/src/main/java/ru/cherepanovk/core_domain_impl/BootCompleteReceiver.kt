package ru.cherepanovk.core_domain_impl

import android.content.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import ru.cherepanovk.core.di.ComponentManager
import ru.cherepanovk.core.di.getOrThrow
import ru.cherepanovk.core_domain_impl.callservices.CallListenerHandlerImpl
import ru.cherepanovk.core_domain_impl.callservices.CallStateStarterJob
import ru.cherepanovk.core_domain_impl.callservices.di.DaggerCallServicesComponent
import javax.inject.Inject

class BootCompleteReceiver : BroadcastReceiver(), CoroutineScope by CoroutineScope(Dispatchers.IO) {

    @Inject
    lateinit var callListenerHandler: CallListenerHandlerImpl

    override fun onReceive(context: Context, intent: Intent) {
        DaggerCallServicesComponent.builder()
            .contextProvider(ComponentManager.getOrThrow())
            .build()
            .injectBootCompleteReceiver(this)

//        callListenerHandler.startCallLister()
//        CallStateStarterJob.enqueueWork(context, intent)
    }
}
