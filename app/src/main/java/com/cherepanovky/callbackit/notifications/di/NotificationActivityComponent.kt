package com.cherepanovky.callbackit.notifications.di

import com.cherepanovky.callbackit.notifications.NotificationActivity
import dagger.Component
import ru.cherepanovk.core.di.dependencies.RootViewProvider
import ru.cherepanovk.core.di.viewmodel.ViewModelModule

@Component(
   modules = [NotificationActivityModule::class]
)
interface NotificationActivityComponent : RootViewProvider {
    fun inject(notificationActivity: NotificationActivity)
}