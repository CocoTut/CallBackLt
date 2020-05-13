package ru.cherepanovk.feature_settings_impl

import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.navigation.NavGraph
import androidx.navigation.fragment.findNavController
import ru.cherepanovk.core.di.ComponentManager
import ru.cherepanovk.core.di.dependencies.FeatureNavigator
import ru.cherepanovk.core.di.getOrThrow
import ru.cherepanovk.core.platform.BaseFragment
import ru.cherepanovk.core.platform.ErrorHandler
import ru.cherepanovk.core.platform.viewBinding
import ru.cherepanovk.core.utils.extentions.observe
import ru.cherepanovk.core.utils.extentions.observeFailure
import ru.cherepanovk.feature_google_calendar_api.data.GoogleAccountFeatureStarter
import ru.cherepanovk.feature_google_calendar_api.data.GoogleCalendarApi
import ru.cherepanovk.feature_settings_impl.databinding.FragmentSettingsBinding
import ru.cherepanovk.feature_settings_impl.di.SettingsComponent
import ru.cherepanovk.imgurtest.utils.extensions.showOrHide
import javax.inject.Inject

class SettingsFragment : BaseFragment(R.layout.fragment_settings) {
    private val model by viewModels<SettingViewModel> { viewModelFactory }
    private val binding: FragmentSettingsBinding by viewBinding(FragmentSettingsBinding::bind)

    @Inject
    lateinit var errorHandler: ErrorHandler

    @Inject
    lateinit var googleAccountFeatureStarter: GoogleAccountFeatureStarter

    @Inject
    lateinit var featureNavigator: FeatureNavigator

    override fun inject(componentManager: ComponentManager) {
        componentManager.getOrThrow<SettingsComponent>()
            .inject(this)
    }

    override fun bindListeners() {
        binding.tvSetGoogleAccount.setOnClickListener {
            model.onAddAccountClick()
            featureNavigator.navigateToFeature(
                navController = findNavController(),
                featureNavGraph =
                googleAccountFeatureStarter.getAddGoogleAccountGraph(findNavController().navInflater)
            )
        }
    }

    override fun bindViewModel() {
        with(model) {
            observe(googleAccount, ::setGoogleAccount)
            observe(logoutVisible, ::setLogoutVisibility)
            observeFailure(failure, errorHandler::onHandleFailure)
        }
    }

    private fun setLogoutVisibility(visible: Boolean) {
        binding.btnLogOut.showOrHide(visible)
    }


    private fun setGoogleAccount(account: String) {
        binding.tvSetGoogleAccount.text =
            if (account.isEmpty())
                getString(R.string.tvSetGoogleAccount)
            else
                account
    }
}