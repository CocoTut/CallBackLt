package ru.cherepanovk.core.di.dependencies

import android.content.Context

interface ContextProvider {
    fun getContext(): Context
}