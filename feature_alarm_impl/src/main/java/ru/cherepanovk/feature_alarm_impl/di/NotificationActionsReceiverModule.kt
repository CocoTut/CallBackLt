package ru.cherepanovk.feature_alarm_impl.di

import dagger.Binds
import dagger.Module
import ru.cherepanovk.feature_alarm_api.data.AlarmNotificationServiceLauncher
import ru.cherepanovk.feature_alarm_impl.callservices.AlarmNotificationServiceLauncherImpl

@Module
interface NotificationActionsReceiverModule {
    @Binds
    fun bindLauncher(launcher: AlarmNotificationServiceLauncherImpl): AlarmNotificationServiceLauncher
}