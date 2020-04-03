package ru.cherepanovk.core.utils.extentions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.Factory
import androidx.lifecycle.ViewModelProviders
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.cherepanovk.core.platform.BaseFragment

inline fun <reified T : ViewModel> Fragment.viewModel(factory: Factory, body: T.() -> Unit): T {
    val vm = ViewModelProvider(this, factory).get(T::class.java)
    vm.body()
    return vm
}

inline fun <reified T : ViewModel> Fragment.viewModel(factory: Factory): T {
    return ViewModelProviders.of(this, factory)[T::class.java]
}

//val BaseFragment.viewContainer: View get() = activity!!.findViewById(R.id.fragmentContainer)

val BaseFragment.appContext: Context get() = activity?.applicationContext!!