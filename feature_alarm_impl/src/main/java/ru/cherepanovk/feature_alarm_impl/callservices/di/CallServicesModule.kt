package ru.cherepanovk.feature_alarm_impl.callservices.di

import dagger.Binds
import dagger.Module
import ru.cherepanovk.feature_alarm_api.data.NotificationChannelCreator
import ru.cherepanovk.feature_alarm_impl.notifications.CallListenerNotificationCreator
import ru.cherepanovk.feature_alarm_impl.notifications.CallListenerNotificationCreatorImpl
import ru.cherepanovk.feature_alarm_impl.notifications.NotificationChannelCreatorImpl

@Module
abstract class CallServicesModule {
   @Binds
   abstract fun bindCallListenerNotificationCreator(callListenerNotificationCreatorImpl: CallListenerNotificationCreatorImpl): CallListenerNotificationCreator

   @Binds
   abstract fun bindNotificationChannelCreator(notificationChannelCreatorImpl: NotificationChannelCreatorImpl)
           : NotificationChannelCreator
}