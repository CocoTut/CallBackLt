package ru.cherepanovk.feature_alarm_impl.callservices.di

import dagger.Component
import ru.cherepanovk.core.di.dependencies.ContextProvider
import ru.cherepanovk.core_preferences_api.di.CorePreferencesApi
import ru.cherepanovk.feature_alarm_impl.alarm.NotificationAlarmService
import ru.cherepanovk.feature_alarm_impl.notifications.NotificationReceiver

@Component(
    dependencies = [
        ContextProvider::class,
        CorePreferencesApi::class
    ],
    modules = [NotificationAlarmServicesModule::class]
)
interface NotificationAlarmServiceComponent {
    fun injectNotificationAlarmService(notificationAlarmService: NotificationAlarmService)

    fun injectNotificationReceiver(notificationReceiver: NotificationReceiver)
}