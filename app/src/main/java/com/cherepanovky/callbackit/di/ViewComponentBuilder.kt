package com.cherepanovky.callbackit.di

import dagger.BindsInstance
import ru.cherepanovk.core.platform.RootViewProvider

interface ViewComponentBuilder<Component> {
    @BindsInstance
    fun rootViewProvider(rootViewProvider: RootViewProvider): ViewComponentBuilder<Component>

    fun build(): Component
}