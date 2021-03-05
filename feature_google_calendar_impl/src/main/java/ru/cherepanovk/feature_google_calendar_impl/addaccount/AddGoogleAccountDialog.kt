package ru.cherepanovk.feature_google_calendar_impl.addaccount

import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.cherepanovk.core.di.ComponentManager
import ru.cherepanovk.core.di.getOrThrow
import ru.cherepanovk.core.exception.Failure
import ru.cherepanovk.core.platform.BaseDialogFragment
import ru.cherepanovk.core.platform.viewBinding
import ru.cherepanovk.core.utils.extentions.observe
import ru.cherepanovk.core.utils.extentions.observeFailure
import ru.cherepanovk.feature_google_calendar_impl.R
import ru.cherepanovk.feature_google_calendar_impl.databinding.DialogAddGoogleAccountBinding
import ru.cherepanovk.feature_google_calendar_impl.data.AccountPermissionManager
import ru.cherepanovk.feature_google_calendar_impl.di.GoogleCalendarApiComponent
import ru.cherepanovk.imgurtest.utils.extensions.showOrGone
import javax.inject.Inject

class AddGoogleAccountDialog : BaseDialogFragment(R.layout.dialog_add_google_account) {
    @Inject
    lateinit var accountPermissionManager: AccountPermissionManager

    private val model: AddGoogleAccountViewModel by viewModels { viewModelFactory }
    private val binding: DialogAddGoogleAccountBinding by viewBinding(DialogAddGoogleAccountBinding::bind)

    override fun inject(componentManager: ComponentManager) {
       componentManager.getOrThrow<GoogleCalendarApiComponent>()
           .getAddGoogleAccountDialogComponent()
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
            observe(isLoading, ::loading)
            observeFailure(failure, ::showFailure)
        }
    }

    private fun showFailure(failure: Failure?) {
        view?.let {
            errorHandler.onHandleFailure(it, failure)
        }

        this.dismiss()
    }

    private fun loading(loading: Boolean) {
        binding.pbAccountDialog.showOrGone(loading)
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
        if (added) {
            this.dismiss()
            findNavController().navigate(R.id.action_addGoogleAccountDialog_to_DialogLoadEvents)
        }
    }

    private fun getGoogleCalendarAccount() {
        accountPermissionManager.chooseAccountViaFragment(this)
    }

    private fun requestPermissionsForAccount(authIntent: Intent) {
        accountPermissionManager.requestPermissionsForAccountViaFragment(this, authIntent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        model.setAccount(accountPermissionManager.getChosenAccountName(requestCode, resultCode, data))
        model.accountGranted(
            accountPermissionManager.isPermissionsForAccountGranted(
                requestCode,
                resultCode,
                data
            )
        )
    }



}