package com.cherepanovky.callbackit.core.di

import com.cherepanovky.callbackit.core.platform.RootViewProvider
import dagger.BindsInstance

interface ViewComponentBuilder<Component> {
    @BindsInstance
    fun rootViewProvider(rootViewProvider: RootViewProvider): ViewComponentBuilder<Component>

    fun build(): Component
}