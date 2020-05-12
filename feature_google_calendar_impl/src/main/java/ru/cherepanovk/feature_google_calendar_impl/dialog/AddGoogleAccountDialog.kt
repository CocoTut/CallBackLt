package ru.cherepanovk.feature_google_calendar_impl.dialog

import android.content.Intent
import androidx.fragment.app.viewModels
import ru.cherepanovk.core.di.ComponentManager
import ru.cherepanovk.core.di.getOrThrow
import ru.cherepanovk.core.platform.BaseDialogFragment
import ru.cherepanovk.core.platform.viewBinding
import ru.cherepanovk.core.utils.extentions.observe
import ru.cherepanovk.core.utils.extentions.observeFailure
import ru.cherepanovk.feature_google_calendar_api.data.GoogleCalendarApi
import ru.cherepanovk.feature_google_calendar_impl.R
import ru.cherepanovk.feature_google_calendar_impl.databinding.DialogAddGoogleAccountBinding
import ru.cherepanovk.feature_google_calendar_impl.dialog.di.DaggerAddGoogleAccountDialogComponent
import javax.inject.Inject

class AddGoogleAccountDialog : BaseDialogFragment(R.layout.dialog_add_google_account) {
    @Inject
    lateinit var googleCalendarApi: GoogleCalendarApi

    private val model: AddGoogleAccountViewModel by viewModels { viewModelFactory }
    private val binding: DialogAddGoogleAccountBinding by viewBinding(DialogAddGoogleAccountBinding::bind)

    override fun inject(componentManager: ComponentManager) {
        DaggerAddGoogleAccountDialogComponent.builder()
            .contextProvider(componentManager.getOrThrow())
            .coreGoogleCalendarApi(componentManager.getOrThrow())
            .corePreferencesApi(componentManager.getOrThrow())
            .rootViewProvider(componentManager.getOrThrow())
            .build()
            .inject(this)
    }

    override fun bindListeners() {
        binding.btnNo.setOnClickListener {
            this.dismiss()
        }
        binding.btnYes.setOnClickListener {
            getGoogleCalendarAccount()
        }
    }

    override fun bindViewModel() {
        with(model) {
            observe(requestPermissionsForAccountEvent, ::requestPermissionsForAccount)
            observe(googleCalendarAccountAddedEvent, ::accountAdded)
            observe(hasAccountAlready, ::setMessage)
            observeFailure(failure, errorHandler::onHandleFailure)
        }
    }

    private fun setMessage(hasAccountAlready: Boolean) {
        binding.tvTitleDialog.setText(
            if (hasAccountAlready)
                R.string.message_change_account
            else
                R.string.messageUseGoogleAccountDialog

        )
    }

    private fun accountAdded(added: Boolean) {
        if (added)
            dismiss()
    }

    private fun getGoogleCalendarAccount() {
        googleCalendarApi.chooseAccountViaFragment(this)
    }

    private fun requestPermissionsForAccount(authIntent: Intent) {
        googleCalendarApi.requestPermissionsForAccountViaFragment(this, authIntent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        model.setAccount(googleCalendarApi.getChosenAccountName(requestCode, resultCode, data))
        model.accountGranted(
            googleCalendarApi.isPermissionsForAccountGranted(
                requestCode,
                resultCode,
                data
            )
        )
    }


}