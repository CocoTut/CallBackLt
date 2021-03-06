package com.cherepanovky.callbackit.notifications.di

import com.cherepanovky.callbackit.notifications.NotificationActivity
import dagger.Component
import ru.cherepanovk.feature_alarm_api.di.FeatureAlarmApi

@Component(
    dependencies = [FeatureAlarmApi::class],
    modules = [NotificationActivityModule::class]
)
interface NotificationActivityComponent{
    fun inject(notificationActivity: NotificationActivity)
}