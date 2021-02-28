package ru.cherepanovk.core.utils.extentions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.Factory
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

inline fun <reified T : ViewModel> AppCompatActivity.viewModel(factory: Factory, body: T.() -> Unit): T {
    val vm = ViewModelProvider(this, factory).get(T::class.java)
    vm.body()
    return vm
}

fun <T> AppCompatActivity.observe(liveData: LiveData<T>, block: (T) -> Unit) {
    liveData.observe(this, Observer(block))
}