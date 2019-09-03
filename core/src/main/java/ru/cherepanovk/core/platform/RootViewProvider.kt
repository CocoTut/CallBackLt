package ru.cherepanovk.core.platform

import android.view.View

class RootViewProvider(private val rootViewProvider: () -> View) {
    val rootView: View get() = rootViewProvider()
}