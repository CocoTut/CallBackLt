package com.cherepanovky.callbackit.features

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.cherepanovky.callbackit.R
import com.cherepanovky.callbackit.core.platform.BaseActivity
import kotlinx.android.synthetic.main.activity_call_back_it_main.*
import ru.cherepanovk.core.di.ComponentManager

class CallBackItMainActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call_back_it_main)

        NavigationUI.setupWithNavController(navigationView, findNavController(R.id.nav_host_fragment))

    }

    override fun inject(componentManager: ComponentManager) {

    }
}
