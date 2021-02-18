package ru.cherepanovk.feature_alarm_api.di

import ru.cherepanovk.feature_alarm_api.data.AlarmApi
import ru.cherepanovk.feature_alarm_api.data.AlarmNotificationServiceLauncher
import ru.cherepanovk.feature_alarm_api.data.CallListenerHandler
import ru.cherepanovk.feature_alarm_api.data.NotificationChannelCreator

interface FeatureAlarmApi {

    fun alarmApi(): AlarmApi

    fun notificationChannelCreator(): NotificationChannelCreator

    fun callListenerStarter(): CallListenerHandler

    fun getAlarmNotificationServiceLauncher(): AlarmNotificationServiceLauncher

}