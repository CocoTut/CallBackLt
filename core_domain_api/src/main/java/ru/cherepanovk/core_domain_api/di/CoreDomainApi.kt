package ru.cherepanovk.core_domain_api.di

import ru.cherepanovk.core_domain_api.data.AlarmApi
import ru.cherepanovk.core_domain_api.data.CallListenerHandler
import ru.cherepanovk.core_domain_api.data.NotificationChannelCreator

interface CoreDomainApi {

    fun alarmApi(): AlarmApi

    fun notificationChannelCreator(): NotificationChannelCreator

    fun callListenerStarter(): CallListenerHandler

}