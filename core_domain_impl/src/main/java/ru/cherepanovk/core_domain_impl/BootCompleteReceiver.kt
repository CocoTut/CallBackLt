package ru.cherepanovk.core_domain_impl

import android.content.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import ru.cherepanovk.core_domain_impl.callservices.CallListenerHandlerImpl
import javax.inject.Inject

class BootCompleteReceiver : BroadcastReceiver(), CoroutineScope by CoroutineScope(Dispatchers.IO) {

    @Inject
    lateinit var callListenerHandler: CallListenerHandlerImpl

    override fun onReceive(context: Context, intent: Intent) {

    }
}
