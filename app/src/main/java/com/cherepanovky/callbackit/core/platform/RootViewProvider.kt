package com.cherepanovky.callbackit.core.platform

import android.view.View

class RootViewProvider(private val rootViewProvider: () -> View) {
    val rootView: View get() = rootViewProvider()
}