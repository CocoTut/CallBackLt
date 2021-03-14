package ru.cherepanovk.core.platform

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import javax.inject.Inject

abstract class BaseFragment(@LayoutRes private val layout: Int) : Fragment(layout), ActivityStarter {

    @Inject
    lateinit var errorHandler: ErrorHandler

    protected abstract fun inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
    }

    override fun onStart() {
        super.onStart()

        bindListeners()
    }


    protected fun firstTimeCreated(savedInstanceState: Bundle?) = savedInstanceState == null

    protected abstract fun bindListeners()

    protected abstract fun bindViewModel()


}
