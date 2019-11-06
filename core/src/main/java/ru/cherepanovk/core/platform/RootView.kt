package ru.cherepanovk.core.platform

import android.view.View

class RootView(private val rootViewProvider: () -> View) {
    val rootView: View get() = rootViewProvider()
}