package ru.cherepanovk.core.platform

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import ru.cherepanovk.core.di.ComponentManager
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity(), ActivityStarter {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        //inject(ComponentManager)
        super.onCreate(savedInstanceState)
    }

    protected abstract fun inject(componentManager: ComponentManager)

    abstract fun fragmentContainer(): View

}