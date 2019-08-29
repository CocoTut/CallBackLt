package com.cherepanovky.callbackit.core.di.viewmodel

import androidx.lifecycle.ViewModelProvider


interface VMFactory {
    fun getVMFac():  ViewModelProvider.Factory
}