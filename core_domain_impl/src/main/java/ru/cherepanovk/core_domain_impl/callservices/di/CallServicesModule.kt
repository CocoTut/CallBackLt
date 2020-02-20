package ru.cherepanovk.core_domain_impl.callservices.di

import dagger.Binds
import dagger.Module
import ru.cherepanovk.core_domain_api.data.NotificationChannelCreator
import ru.cherepanovk.core_domain_impl.notifications.CallListenerNotificationCreator
import ru.cherepanovk.core_domain_impl.notifications.CallListenerNotificationCreatorImpl
import ru.cherepanovk.core_domain_impl.notifications.NotificationChannelCreatorImpl

@Module
abstract class CallServicesModule {
   @Binds
   abstract fun bindCallListenerNotificationCreator(callListenerNotificationCreatorImpl: CallListenerNotificationCreatorImpl): CallListenerNotificationCreator

   @Binds
   abstract fun bindNotificationChannelCreator(notificationChannelCreatorImpl: NotificationChannelCreatorImpl)
           : NotificationChannelCreator
}