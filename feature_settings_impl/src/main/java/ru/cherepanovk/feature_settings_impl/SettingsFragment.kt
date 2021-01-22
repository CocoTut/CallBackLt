package ru.cherepanovk.feature_settings_impl

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.cherepanovk.core.di.ComponentManager
import ru.cherepanovk.core.di.dependencies.FeatureNavigator
import ru.cherepanovk.core.di.getOrThrow
import ru.cherepanovk.core.platform.BaseFragment
import ru.cherepanovk.core.platform.viewBinding
import ru.cherepanovk.core.utils.extentions.observe
import ru.cherepanovk.core.utils.extentions.observeEvent
import ru.cherepanovk.core.utils.extentions.observeFailure
import ru.cherepanovk.feature_google_calendar_api.data.GoogleAccountFeatureStarter
import ru.cherepanovk.feature_settings_impl.RingtoneChooser.RequiredStater.START_ACTIVITY
import ru.cherepanovk.feature_settings_impl.databinding.FragmentSettingsBinding
import ru.cherepanovk.feature_settings_impl.di.SettingsComponent
import ru.cherepanovk.imgurtest.utils.extensions.showOrGone
import javax.inject.Inject

private const val REQUEST_RINGTONE = 999
class SettingsFragment : BaseFragment(R.layout.fragment_settings) {
    private val model by viewModels<SettingViewModel> { viewModelFactory }
    private val binding: FragmentSettingsBinding by viewBinding(FragmentSettingsBinding::bind)

    @Inject
    lateinit var googleAccountFeatureStarter: GoogleAccountFeatureStarter

    @Inject
    lateinit var featureNavigator: FeatureNavigator

    @Inject
    lateinit var ringtoneChooser: RingtoneChooser

    override fun inject(componentManager: ComponentManager) {
        componentManager.getOrThrow<SettingsComponent>()
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.tvToolbarTitle.text = getString(R.string.title_toolbar_settings)
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

        binding.btnLogOut.setOnClickListener {
            model.logout()
        }

        binding.swIncoming.setOnCheckedChangeListener { _, checked ->
            model.setAllIncoming(checked)
        }

        binding.swIncomingMissed.setOnCheckedChangeListener { _, checked ->
            model.setMissedIncoming(checked)
        }

        binding.swOutgoing.setOnCheckedChangeListener { _, checked ->
            model.setAllOutgoing(checked)
        }

        binding.clSound.setOnClickListener {
            model.onRingtoneClick()
        }

        binding.swWhatsApp.setOnCheckedChangeListener { _, checked ->
            model.setWhatsAppEnabled(checked)
        }

        binding.toolbar.ivBack.setOnClickListener { requireActivity().onBackPressed() }
    }

    override fun bindViewModel() {
        with(model) {
            observe(googleAccount, ::setGoogleAccount)
            observe(logoutVisible, ::setLogoutVisibility)
            observe(allIncomingChecked, binding.swIncoming::setChecked)
            observe(allOutgoingChecked, binding.swOutgoing::setChecked)
            observe(missedIncomingChecked, binding.swIncomingMissed::setChecked)
            observe(missedIncomingEnabled, binding.swIncomingMissed::setEnabled)
            observe(whatsAppEnabled, binding.swWhatsApp::setChecked)
            observe(ringtoneTitle, ::setRingtoneTitle)
            observeEvent(chosenRingtone, ::chooseRingtone)
            observeFailure(failure, errorHandler::onHandleFailure)
        }
    }

    private fun setRingtoneTitle(ringtoneUri: Uri) {
        binding.tvCurrentRingtone.text =
            RingtoneManager.getRingtone(context, ringtoneUri).getTitle(context)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == REQUEST_RINGTONE) {
            data?.getParcelableExtra<Uri>(RingtoneManager.EXTRA_RINGTONE_PICKED_URI)?.let {
                model.setPickedRingtone(it)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        model.onResume()
    }

    private fun chooseRingtone(ringtoneData: Pair<String, String>) {
        ringtoneChooser.chooseRingtone(requireContext(), ringtoneData.first, ringtoneData.second)
            .run {
                when(first) {
                    START_ACTIVITY -> startActivity(second)
                    else -> startActivityForResult(second, REQUEST_RINGTONE)
                }
            }
    }

    private fun setLogoutVisibility(visible: Boolean) {
        binding.btnLogOut.showOrGone(visible)
    }


    private fun setGoogleAccount(account: String) {
        binding.tvSetGoogleAccount.text =
            if (account.isEmpty())
                getString(R.string.tvSetGoogleAccount)
            else
                account
    }
}