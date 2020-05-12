package ru.cherepanovk.feature_settings_impl

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.cherepanovk.core.interactor.UseCase
import ru.cherepanovk.core.platform.BaseViewModel
import ru.cherepanovk.core.platform.SingleLiveEvent
import ru.cherepanovk.feature_google_calendar_api.data.GoogleCalendarApi
import javax.inject.Inject


class SettingViewModel @Inject constructor(
    private val getGoogleAccount: GetGoogleAccount,
    private val googleCalendarApi: GoogleCalendarApi
) : BaseViewModel() {
    private val _googleAccount = MutableLiveData<String>()
    val googleAccount: LiveData<String>
        get() = _googleAccount

    val logoutVisible = Transformations.map(googleAccount) { it.isNotEmpty() }

    private val _requestPermissionsForAccountEvent = SingleLiveEvent<Intent>()
    val requestPermissionsForAccountEvent: LiveData<Intent>
        get() = _requestPermissionsForAccountEvent

    init {
        loadAccount()
    }

    fun onAddAccountClick() {
        observeAccountState()
    }
    private fun observeAccountState() {
        launch {
            googleCalendarApi.accountSaved().collect {
                if (it)
                    loadAccount()
            }
        }
    }

    private fun loadAccount() {
        launch {
            getGoogleAccount(UseCase.None()) {
                it.handleSuccess { account ->
                    _googleAccount.postValue(account)
                }
            }
        }
    }

}