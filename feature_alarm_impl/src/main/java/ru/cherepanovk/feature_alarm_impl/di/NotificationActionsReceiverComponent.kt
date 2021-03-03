package ru.cherepanovk.feature_alarm_impl.di

import dagger.Component
import ru.cherepanovk.core.di.dependencies.ContextProvider
import ru.cherepanovk.feature_alarm_impl.notifications.NotificationActionsReceiver

@Component(
    modules = [NotificationActionsReceiverModule::class],
    dependencies = [ContextProvider::class]
)
interface NotificationActionsReceiverComponent {
    fun inject(receiver: NotificationActionsReceiver)
}