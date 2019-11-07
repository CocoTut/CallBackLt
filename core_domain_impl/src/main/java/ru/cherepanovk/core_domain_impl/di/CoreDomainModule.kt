package ru.cherepanovk.core_domain_impl.di

import android.app.AlarmManager
import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.cherepanovk.core_domain_api.data.AlarmApi
import ru.cherepanovk.core_domain_impl.CallBackAlarm

@Module
abstract class  CoreDomainModule {
    @Binds
    abstract fun bindAlarmApi(callBackAlarm: CallBackAlarm): AlarmApi

    @Module
    companion object{
        @Provides
        @JvmStatic
        fun provideAlarmManager(context: Context) =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

}