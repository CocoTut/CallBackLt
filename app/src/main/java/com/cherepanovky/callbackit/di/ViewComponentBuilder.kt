package com.cherepanovky.callbackit.di

import dagger.BindsInstance
import ru.cherepanovk.core.platform.RootView

interface ViewComponentBuilder<Component> {
    @BindsInstance
    fun rootViewProvider(rootView: RootView): ViewComponentBuilder<Component>

    fun build(): Component
}