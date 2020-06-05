package ru.cherepanovk.feature_alarm_impl.di

import android.app.AlarmManager
import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.cherepanovk.feature_alarm_api.data.AlarmApi
import ru.cherepanovk.feature_alarm_api.data.CallListenerHandler
import ru.cherepanovk.feature_alarm_api.data.NotificationChannelCreator
import ru.cherepanovk.feature_alarm_impl.CallBackAlarm
import ru.cherepanovk.feature_alarm_impl.callservices.CallListenerHandlerImpl
import ru.cherepanovk.feature_alarm_impl.notifications.NotificationChannelCreatorImpl

@Module
abstract class  CoreDomainModule {
    @Binds
    abstract fun bindAlarmApi(callBackAlarm: CallBackAlarm): AlarmApi

    @Binds
    abstract fun bindNotificationChannelCreator(notificationChannelCreatorImpl: NotificationChannelCreatorImpl)
            : NotificationChannelCreator

    @Binds
    abstract fun bindCallListenerHandler(callListenerHandlerImpl: CallListenerHandlerImpl): CallListenerHandler


    @Module
    companion object{
        @Provides
        @JvmStatic
        fun provideAlarmManager(context: Context) =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

}