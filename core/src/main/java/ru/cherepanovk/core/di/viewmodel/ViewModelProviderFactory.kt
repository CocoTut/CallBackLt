package ru.cherepanovk.core.di.viewmodel

import androidx.lifecycle.ViewModelProvider

interface ViewModelProviderFactory {
    fun viewModelFactory(): ViewModelProvider.Factory
}