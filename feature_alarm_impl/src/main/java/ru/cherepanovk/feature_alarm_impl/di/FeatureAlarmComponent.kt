package ru.cherepanovk.feature_alarm_impl.di

import dagger.Component
import ru.cherepanovk.core.di.dependencies.AppConfigProvider
import ru.cherepanovk.core.di.dependencies.ContextProvider
import ru.cherepanovk.core_db_api.di.CoreDbApi
import ru.cherepanovk.core_preferences_api.di.CorePreferencesApi
import ru.cherepanovk.feature_alarm_api.di.FeatureAlarmApi
import ru.cherepanovk.feature_alarm_impl.BootCompleteReceiver
import ru.cherepanovk.feature_alarm_impl.callservices.di.CallServicesComponent
import ru.cherepanovk.feature_alarm_impl.callservices.di.NotificationAlarmServiceComponent
import ru.cherepanovk.feature_alarm_impl.notifications.NotificationActionsReceiver

@Component(
    dependencies = [
        ContextProvider::class,
        CoreDbApi::class,
        CorePreferencesApi::class,
        AppConfigProvider::class
    ],
    modules = [CoreDomainModule::class]
)
interface FeatureAlarmComponent : FeatureAlarmApi {

    fun inject(bootCompleteReceiver: BootCompleteReceiver)
    fun inject(receiver: NotificationActionsReceiver)

    fun getCallServicesComponent(): CallServicesComponent
    fun getNotificationAlarmServiceComponent(): NotificationAlarmServiceComponent
}