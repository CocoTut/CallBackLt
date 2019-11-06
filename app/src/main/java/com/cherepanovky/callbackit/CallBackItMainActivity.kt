package com.cherepanovky.callbackit

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.findNavController
import com.cherepanovky.callbackit.di.DaggerMainActivityComponent
import com.cherepanovky.callbackit.di.FeatureProxyInjector
import com.cherepanovky.callbackit.di.MainActivityModule
import kotlinx.android.synthetic.main.activity_route.*
import ru.cherepanovk.core.di.ComponentManager
import ru.cherepanovk.core.platform.BaseActivity
import ru.cherepanovk.core.platform.RootView

class CallBackItMainActivity : BaseActivity() {

    override fun fragmentContainer(): View  = fragmentContainer

    private val navController: NavController by lazy { findNavController(R.id.nav_host_fragment) }
    private var startDestination = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_route)
        inject(ComponentManager)
        setNavigation()

        bindListeners()

    }

    override fun inject(componentManager: ComponentManager) {
        DaggerMainActivityComponent.builder()
            .mainActivityModule(MainActivityModule(fragmentContainer()))
            .build()
            .also { componentManager.put(it) }
            .inject(this)

    }

    private fun setNavigation() {
        val navGraph = FeatureProxyInjector.getEventsFeature().eventsFeatureStarter()
            .getNavGraph(navController.navInflater)
        startDestination = navGraph.startDestination
        navController.graph = navGraph
    }

    private fun bindListeners() {

        ivToolbarBurger.setOnClickListener {
            fragmentContainer.openDrawer(GravityCompat.START, true)
        }

        navigationView.setNavigationItemSelectedListener { menuItem ->
            openFeatureFromMenu(menuItem.itemId)
            false
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                startDestination -> setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                else -> setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        }
    }

    private fun openFeatureFromMenu(feature: Int) {
        when (feature) {
            R.id.settings -> openSettingsFeature()
            else -> Toast.makeText(this, "Not implemented", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setDrawerLockMode(lockMode: Int) {
        ivToolbarBurger.visibility = when (lockMode) {
            DrawerLayout.LOCK_MODE_UNLOCKED -> View.VISIBLE
            else -> View.GONE
        }

        fragmentContainer.setDrawerLockMode(lockMode)
    }

    private fun openSettingsFeature() {
        val settingsGraph = FeatureProxyInjector.getSettingsFeature()
            .settingsFeatureStarter()
            .getNavGraph(navController.navInflater)
        navigateToFeature(settingsGraph)
    }


    private fun navigateToFeature(featureNavGraph: NavGraph) {
        navController.graph.addDestination(featureNavGraph)
        navController.navigate(featureNavGraph.id)
    }


}
