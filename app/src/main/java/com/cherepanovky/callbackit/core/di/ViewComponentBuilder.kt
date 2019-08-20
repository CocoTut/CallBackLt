package com.cherepanovky.callbackit.core.di

import dagger.BindsInstance
import ru.cherepanovk.imgurtest.core.platform.RootViewProvider

interface ViewComponentBuilder<Component> {
    @BindsInstance
    fun rootViewProvider(rootViewProvider: RootViewProvider): ViewComponentBuilder<Component>

    fun build(): Component
}