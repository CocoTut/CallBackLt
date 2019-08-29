package com.cherepanovky.callbackit.core.di.viewmodel

import androidx.lifecycle.ViewModelProvider


interface ViewModelProvider {
    fun getViewModelFactory():  ViewModelProvider.Factory
}