package com.cherepanovky.callbackit

import android.accounts.AccountManager
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavGraph
import com.cherepanovky.callbackit.di.DaggerMainActivityComponent
import com.cherepanovky.callbackit.di.FeatureProxyInjector
import com.cherepanovky.callbackit.di.MainActivityModule
import kotlinx.android.synthetic.main.activity_route.*
import ru.cherepanovk.core.di.ComponentManager
import ru.cherepanovk.core.di.getOrThrow
import ru.cherepanovk.core.platform.BaseActivity
import ru.cherepanovk.core.utils.extentions.observe
import ru.cherepanovk.core.utils.extentions.viewModel
import ru.cherepanovk.core_network_impl.di.DaggerNetworkApiComponent
import ru.cherepanovk.feature_alarm_impl.di.DaggerCoreDomainComponent
import javax.inject.Inject

private const val REQUEST_ACCOUNT_PICKER = 18940
class CallBackItMainActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override var navHost = R.id.nav_host_fragment


    override fun fragmentContainer(): View  = fragmentContainer


    private lateinit var model: CallBackItMainViewModel

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
            .coreDomainApi(
                DaggerCoreDomainComponent.builder()
                .contextProvider(componentManager.getOrThrow())
                    .build()
            )
            .coreNetworkApi(
                DaggerNetworkApiComponent.builder()
                    .contextProvider(componentManager.getOrThrow())
                    .build()
            )
            .build()
            .also { componentManager.put(it) }
            .inject(this)

        model = viewModel(viewModelFactory){
            observe(googleCalendarAccount, ::auth)
        }
    }

    private fun auth(calendarIntent: Intent) {
        startActivityForResult(
            calendarIntent,
            REQUEST_ACCOUNT_PICKER
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_ACCOUNT_PICKER && resultCode == RESULT_OK && data != null && data.extras != null
        ) {
            val accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME)

        }
    }


    private fun setNavigation() {
        val navGraph = FeatureProxyInjector.getEventsFeature().eventsFeatureStarter()
            .getEventsNavGraph(navController.navInflater)
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
