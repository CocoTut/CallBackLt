package com.cherepanovky.callbackit.core.platform

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.cherepanovk.core.di.ComponentManager

import javax.inject.Inject

abstract class BaseFragment(@LayoutRes private val layout: Int) : Fragment(), ActivityStarter {

    protected val componentManager get() = ComponentManager

    override fun onAttach(context: Context?) {
//        inject(ComponentManager)
        super.onAttach(context)
    }

    protected abstract fun inject(componentManager: ComponentManager)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(layout, container, false)

    protected fun firstTimeCreated(savedInstanceState: Bundle?) = savedInstanceState == null

    protected fun showLoading(isLoading: Boolean) {
//        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


}
