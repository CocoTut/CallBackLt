package ru.cherepanovk.core.platform

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import ru.cherepanovk.core.di.ComponentManager

import javax.inject.Inject

abstract class BaseFragment(@LayoutRes private val layout: Int) : Fragment(layout), ActivityStarter {

    private val componentManager: ComponentManager
        get() = ComponentManager

    @Inject
    lateinit var errorHandler: ErrorHandler

    protected abstract fun inject(componentManager: ComponentManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject(componentManager)

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
