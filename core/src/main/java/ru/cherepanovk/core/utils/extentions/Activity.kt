package ru.cherepanovk.core.utils.extentions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.Factory
import androidx.lifecycle.ViewModelProviders
import androidx.appcompat.app.AppCompatActivity

inline fun <reified T : ViewModel> AppCompatActivity.viewModel(factory: Factory, body: T.() -> Unit): T {
    val vm = ViewModelProviders.of(this, factory)[T::class.java]
    vm.body()
    return vm
}