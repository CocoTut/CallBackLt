package ru.cherepanovk.feature_alarm_impl.callservices.di

import dagger.Binds
import dagger.Module
import ru.cherepanovk.feature_alarm_impl.notifications.CallListenerNotificationCreator
import ru.cherepanovk.feature_alarm_impl.notifications.CallListenerNotificationCreatorImpl

@Module
abstract class NotificationAlarmServicesModule {
   @Binds
   abstract fun bindCallListenerNotificationCreator(callListenerNotificationCreatorImpl: CallListenerNotificationCreatorImpl): CallListenerNotificationCreator
}