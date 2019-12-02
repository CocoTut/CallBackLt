package com.cherepanovky.callbackit.notifications.di

import android.view.View
import dagger.Module
import dagger.Provides
import ru.cherepanovk.core.platform.RootView

@Module
class NotificationActivityModule(private val rootView: View) {
    @Provides
    fun provideRootView(): RootView = RootView { rootView }
}