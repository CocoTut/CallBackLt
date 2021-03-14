package ru.cherepanovk.feature_alarm_impl.callservices.di

import dagger.Subcomponent
import ru.cherepanovk.feature_alarm_impl.alarm.NotificationAlarmService
import ru.cherepanovk.feature_alarm_impl.notifications.NotificationReceiver

@Subcomponent(modules = [NotificationAlarmServicesModule::class])
interface NotificationAlarmServiceComponent {
    fun injectNotificationAlarmService(notificationAlarmService: NotificationAlarmService)

    fun injectNotificationReceiver(notificationReceiver: NotificationReceiver)
}