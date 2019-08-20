package com.cherepanovky.callbackit.core.extention

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.Factory
import androidx.lifecycle.ViewModelProviders
import android.content.Context

import android.view.View
import androidx.fragment.app.Fragment
import com.cherepanovky.callbackit.R
import com.cherepanovky.callbackit.core.platform.BaseActivity
import com.cherepanovky.callbackit.core.platform.BaseFragment

inline fun <reified T : ViewModel> Fragment.viewModel(factory: Factory, body: T.() -> Unit): T {
    val vm = ViewModelProviders.of(this, factory)[T::class.java]
    vm.body()
    return vm
}

inline fun <reified T : ViewModel> Fragment.viewModel(factory: Factory): T {
    return ViewModelProviders.of(this, factory)[T::class.java]
}

val BaseFragment.viewContainer: View get() = activity!!.findViewById(R.id.fragmentContainer)

val BaseFragment.appContext: Context get() = activity?.applicationContext!!