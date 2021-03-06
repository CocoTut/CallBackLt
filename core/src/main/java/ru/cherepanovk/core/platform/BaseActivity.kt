package ru.cherepanovk.core.platform

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController

abstract class BaseActivity : AppCompatActivity(), ActivityStarter {

    protected abstract var navHost: Int

    protected var startDestination = -1

    protected val navController: NavController by lazy { findNavController(navHost) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    protected abstract fun inject()

    abstract fun fragmentContainer(): View

}