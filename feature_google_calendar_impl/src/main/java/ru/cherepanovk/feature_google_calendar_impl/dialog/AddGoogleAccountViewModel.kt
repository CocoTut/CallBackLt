package ru.cherepanovk.feature_google_calendar_impl.dialog

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.cherepanovk.core.platform.BaseViewModel
import ru.cherepanovk.core.platform.SingleLiveEvent
import ru.cherepanovk.core_preferences_api.data.PreferencesApi
import ru.cherepanovk.feature_google_calendar_impl.dialog.domain.CheckGoogleAccount
import javax.inject.Inject

class AddGoogleAccountViewModel @Inject constructor(
    private val preferencesApi: PreferencesApi,
    private val checkGoogleAccount: CheckGoogleAccount
) : BaseViewModel() {

    private val _googleCalendarAccountAddedEvent = SingleLiveEvent<Boolean>()
    val googleCalendarAccountAddedEvent: LiveData<Boolean>
        get() = _googleCalendarAccountAddedEvent

    private val _requestPermissionsForAccountEvent = SingleLiveEvent<Intent>()
    val requestPermissionsForAccountEvent: LiveData<Intent>
        get() = _requestPermissionsForAccountEvent

    private val _hasAccountAlready = MutableLiveData<Boolean>()
    val hasAccountAlready: LiveData<Boolean>
        get() = _hasAccountAlready

    private lateinit var accountName: String

    init {
        _hasAccountAlready.postValue(preferencesApi.getGoogleAccount().isNotEmpty())
    }

    fun setAccount(chosenAccountName: String?) {
        chosenAccountName?.run {
            accountName = chosenAccountName
            launch(Dispatchers.IO) {
                checkGoogleAccount(chosenAccountName) {
                    it.handleSuccess { accountAvailability ->
                        if (accountAvailability.available) {
                            _googleCalendarAccountAddedEvent.postValue(true)
                            saveAccount(chosenAccountName)
                        } else
                            _requestPermissionsForAccountEvent.postValue(accountAvailability.userAuthIntent)
                    }
                }
            }

        }

    }

    fun accountGranted(granted: Boolean) {
        if (granted) {
//            saveAccount(accountName)
            _googleCalendarAccountAddedEvent.postValue(true)
        }
    }

    private fun saveAccount(account: String) {
        preferencesApi.setGoogleAccount(account)
        _googleCalendarAccountAddedEvent.postValue(true)
    }
}