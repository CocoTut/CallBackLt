package com.cherepanovky.callbackit.receivers.di

import com.cherepanovky.callbackit.receivers.CallStateReceiver
import dagger.Component
import ru.cherepanovk.core_domain_api.di.CoreDomainApi

@Component(dependencies = [CoreDomainApi::class])
interface CallStateComponent {

    fun injectCallStateReceiver(callStateReceiver: CallStateReceiver)
}