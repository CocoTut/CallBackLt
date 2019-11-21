package ru.cherepanovk.core.di.dependencies

import dagger.BindsInstance
import ru.cherepanovk.core.platform.RootView

interface ViewComponentBuilder<Component> {
    @BindsInstance
    fun rootViewProvider(rootView: RootView): ViewComponentBuilder<Component>

    fun build(): Component
}