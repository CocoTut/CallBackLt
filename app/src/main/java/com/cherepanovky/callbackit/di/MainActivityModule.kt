package com.cherepanovky.callbackit.di

import android.view.View
import dagger.Module
import dagger.Provides
import ru.cherepanovk.core.platform.RootView

@Module
class MainActivityModule(private val rootView: View) {

    @Provides
    fun provideRootView(): RootView = RootView { rootView }
}