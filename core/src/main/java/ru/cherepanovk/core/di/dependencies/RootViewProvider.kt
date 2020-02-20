package ru.cherepanovk.core.di.dependencies

import ru.cherepanovk.core.platform.RootView

interface RootViewProvider {
    fun rootView(): RootView
}