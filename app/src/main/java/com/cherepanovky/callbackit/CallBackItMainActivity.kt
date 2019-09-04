package com.cherepanovky.callbackit

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.findNavController
import com.cherepanovky.callbackit.di.FeatureProxyInjector
import kotlinx.android.synthetic.main.activity_route.*
import ru.cherepanovk.core.di.ComponentManager
import ru.cherepanovk.core.platform.BaseActivity
import ru.cherepanovk.feature_settings_impl.SettingsFeatureStarterImpl

class CallBackItMainActivity : BaseActivity() {

    private val navController: NavController by lazy { findNavController(R.id.nav_host_fragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_route)
        val navGraph = FeatureProxyInjector.getEventsFeature().eventsFeatureStarter().getNavGraph( navController.navInflater)
        navController.graph = navGraph

        btn.setOnClickListener {
           val settingsGraph =  SettingsFeatureStarterImpl.getNavGraph( navController.navInflater)
            navController.graph = settingsGraph
        }
    }

    override fun inject(componentManager: ComponentManager) {

    }
}
