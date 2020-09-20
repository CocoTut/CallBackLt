package ru.cherepanovk.feature_alarm_impl.di

import dagger.Component
import ru.cherepanovk.core_db_api.di.CoreDbApi
import ru.cherepanovk.feature_alarm_api.di.FeatureAlarmApi
import ru.cherepanovk.feature_alarm_impl.BootCompleteReceiver


@Component(
    dependencies = [
        FeatureAlarmApi::class,
        CoreDbApi::class
    ]
)
interface BootRecreateRemindersComponent {
    fun inject(bootCompleteReceiver: BootCompleteReceiver)
}