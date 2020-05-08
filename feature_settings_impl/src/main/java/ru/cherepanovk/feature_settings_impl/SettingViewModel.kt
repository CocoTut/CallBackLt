package ru.cherepanovk.feature_settings_impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.launch
import ru.cherepanovk.core.interactor.UseCase
import ru.cherepanovk.core.platform.BaseViewModel
import ru.cherepanovk.core_preferences_api.data.PreferencesApi
import javax.inject.Inject

class SettingViewModel @Inject constructor(
    private val getGoogleAccount: GetGoogleAccount,
    private val preferencesApi: PreferencesApi
) : BaseViewModel() {
    private val _googleAccount = MutableLiveData<String>()
    val googleAccount: LiveData<String>
        get() = _googleAccount

    val logoutVisible = Transformations.map(googleAccount) { it.isNotEmpty() }

    init {
        loadAccount()
    }


    fun onAccountClick() {

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

    fun setAccount(chosenAccountName: String?) {
        chosenAccountName?.run {
            preferencesApi.setGoogleAccount(chosenAccountName)
            loadAccount()
        }
    }

}