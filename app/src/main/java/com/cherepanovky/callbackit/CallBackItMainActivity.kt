package com.cherepanovky.callbackit

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
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
import ru.cherepanovk.core.exception.Failure
import ru.cherepanovk.core.platform.BaseActivity
import ru.cherepanovk.core.platform.ErrorHandler
import ru.cherepanovk.core.utils.extentions.observe
import ru.cherepanovk.core.utils.extentions.viewModel
import ru.cherepanovk.core.utils.getEmailIntent
import ru.cherepanovk.core.utils.getPrivacyUrlIntent
import ru.cherepanovk.core.utils.getRateUrl
import ru.cherepanovk.feature_alarm_impl.di.DaggerFeatureAlarmComponent
import javax.inject.Inject


class CallBackItMainActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var errorHandler: ErrorHandler

    override var navHost = R.id.nav_host_fragment


    override fun fragmentContainer(): View = fragmentContainer


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
            .featureAlarmApi(
                DaggerFeatureAlarmComponent.builder()
                    .contextProvider(componentManager.getOrThrow())
                    .build()
            )
            .corePreferencesApi(ComponentManager.getOrThrow())
            .appConfigProvider(ComponentManager.getOrThrow())
            .build()
            .also { componentManager.put(it) }
            .inject(this)

        model = viewModel(viewModelFactory) {
            observe(accountName, ::setAccountName)
            observe(appVersion, tvApplicationVersion::setText)
        }
    }

    private fun setAccountName(accountName: String) {
        navigationView.getHeaderView(0)
            .findViewById<TextView>(R.id.tvAccountEmail).text = accountName
    }


    private fun setNavigation() {
        val navGraph = FeatureProxyInjector.getEventsFeature().eventsFeatureStarter()
            .getEventsNavGraph(navController.navInflater)
        startDestination = navGraph.startDestination
        navController.graph = navGraph
    }

    private fun bindListeners() {

        ivToolbarBurger.setOnClickListener {
            model.initAccountName()
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
            R.id.privacy -> openPrivacy()
            R.id.rate -> openRate()
            R.id.contact_by_email -> openEmail()
            else -> errorHandler.onHandleFailure(Failure.UnexpectedError)
        }
    }

    private fun openEmail() {
        try {
            startActivity(getEmailIntent(this))
        } catch (e: Exception) {
            e.printStackTrace()
            errorHandler.onHandleFailure(Failure.NoEmailApplication)
        }
    }

    private fun openPrivacy() {
        try {
            startActivity(getPrivacyUrlIntent(this))
        } catch (e: Exception) {
            e.printStackTrace()
            errorHandler.onHandleFailure(Failure.UrlError)
        }
    }

    private fun openRate() {
        try {
            startActivity(getRateUrl(this))
        } catch (e: Exception) {
            e.printStackTrace()
            errorHandler.onHandleFailure(Failure.UrlError)
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
