package com.cherepanovky.callbackit

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import com.cherepanovky.callbackit.databinding.ActivityMainBinding
import com.cherepanovky.callbackit.databinding.ActivityRouteBinding
import com.cherepanovky.callbackit.di.DaggerMainActivityComponent
import ru.cherepanovk.core.di.DI
import ru.cherepanovk.core.di.dependencies.AppConfigProvider
import ru.cherepanovk.core.di.dependencies.FeatureNavigator
import ru.cherepanovk.core.exception.Failure
import ru.cherepanovk.core.platform.BaseActivity
import ru.cherepanovk.core.platform.ErrorHandler
import ru.cherepanovk.core.platform.viewBinding
import ru.cherepanovk.core.utils.extentions.observe
import ru.cherepanovk.core.utils.extentions.viewModel
import ru.cherepanovk.core.utils.getEmailIntent
import ru.cherepanovk.core.utils.getPrivacyUrlIntent
import ru.cherepanovk.core.utils.getRateUrl
import ru.cherepanovk.core_preferences_api.di.CorePreferencesApi
import ru.cherepanovk.feature_alarm_api.di.FeatureAlarmApi
import ru.cherepanovk.feature_events_api.EventsFeatureApi
import ru.cherepanovk.feature_settings_api.SettingsFeatureApi
import javax.inject.Inject


class CallBackItMainActivity : BaseActivity() {

    private val binding: ActivityRouteBinding by viewBinding(ActivityRouteBinding::inflate)

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var errorHandler: ErrorHandler

    @Inject
    lateinit var featureNavigator: FeatureNavigator

    override var navHost = R.id.nav_host_fragment


    override fun fragmentContainer(): View = binding.fragmentContainer



    private lateinit var model: CallBackItMainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        inject()
        setNavigation()

        bindListeners()
    }

    override fun inject() {
        DaggerMainActivityComponent.builder()
            .featureAlarmApi(DI.getDependency(FeatureAlarmApi::class.java))
            .corePreferencesApi(DI.getDependency(CorePreferencesApi::class.java))
            .appConfigProvider(DI.getDependency(AppConfigProvider::class.java))
            .build()
            .inject(this)

        model = viewModel(viewModelFactory) {
//            observe(accountName, ::setAccountName)
//            observe(appVersion, ::setApplicationVersion)
        }
    }

    private fun setApplicationVersion(version: String) {
        binding.tvApplicationVersion.apply {
            text = version
            contentDescription =context.getString(R.string.main_screen_content_description_app_version, version)
        }
    }

    private fun setAccountName(accountName: String) {
        binding.navigationView.getHeaderView(0)
            .findViewById<TextView>(R.id.tvAccountEmail).apply {
                text = accountName
                contentDescription =
                    context.getString(R.string.main_screen_content_description_account, accountName)
            }
    }


    private fun setNavigation() {
        val navGraph = DI.getFeature(EventsFeatureApi::class.java).eventsFeatureStarter()
            .getEventsNavGraph(navController.navInflater)
        startDestination = navGraph.startDestination
        navController.graph = navGraph
    }

    private fun bindListeners() {

        binding.ivToolbarBurger.setOnClickListener {
            model.initAccountName()
            binding.fragmentContainer.openDrawer(GravityCompat.START, true)
        }

        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
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
            else -> errorHandler.onHandleFailure(fragmentContainer(), Failure.UnexpectedError)
        }
    }

    private fun openEmail() {
        try {
            startActivity(getEmailIntent(this))
        } catch (e: Exception) {
            e.printStackTrace()
            errorHandler.onHandleFailure(fragmentContainer(), Failure.NoEmailApplication)
        }
    }

    private fun openPrivacy() {
        try {
            startActivity(getPrivacyUrlIntent(this))
        } catch (e: Exception) {
            e.printStackTrace()
            errorHandler.onHandleFailure(fragmentContainer(), Failure.UrlError)
        }
    }

    private fun openRate() {
        try {
            startActivity(getRateUrl(this))
        } catch (e: Exception) {
            e.printStackTrace()
            errorHandler.onHandleFailure(fragmentContainer(), Failure.UrlError)
        }
    }

    private fun setDrawerLockMode(lockMode: Int) {
        binding.ivToolbarBurger.visibility = when (lockMode) {
            DrawerLayout.LOCK_MODE_UNLOCKED -> View.VISIBLE
            else -> View.GONE
        }

        binding.fragmentContainer.setDrawerLockMode(lockMode)
    }

    private fun openSettingsFeature() {
        val settingsGraph = DI.getFeature(SettingsFeatureApi::class.java)
            .settingsFeatureStarter()
            .getNavGraph(navController.navInflater)
        featureNavigator.navigateToFeature(navController, settingsGraph)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBundle(NAV_GRAPH, navController.saveState())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        navController.restoreState(savedInstanceState.getBundle(NAV_GRAPH))
    }

    companion object {
        private const val NAV_GRAPH = "NAV_GRAPH"
    }
}
