package ru.cherepanovk.core_domain_impl.callservices

import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService
import ru.cherepanovk.core.di.ComponentManager
import ru.cherepanovk.core.di.getOrThrow
import ru.cherepanovk.core_domain_impl.callservices.di.DaggerCallServicesComponent
import javax.inject.Inject

class CallStateStarterJob : JobIntentService() {


    @Inject
    lateinit var callListenerHandler: CallListenerHandlerImpl

    override fun onHandleWork(intent: Intent) {
        super.onCreate()
        DaggerCallServicesComponent.builder()
            .contextProvider(ComponentManager.getOrThrow())
            .build()
            .injectCallStateStarterJob(this)

        callListenerHandler.startCallLister()
        stopSelf()
    }

    companion object {
        private const val JOB_ID = 1001
        fun enqueueWork(context: Context, intent: Intent) = enqueueWork(
            context,
            CallStateStarterJob::class.java,
            JOB_ID,
            intent
        )
    }

}