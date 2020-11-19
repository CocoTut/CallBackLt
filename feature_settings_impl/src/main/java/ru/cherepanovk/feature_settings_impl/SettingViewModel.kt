package ru.cherepanovk.feature_settings_impl

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.cherepanovk.core.acc.Event
import ru.cherepanovk.core.config.AppConfig
import ru.cherepanovk.core.interactor.UseCase
import ru.cherepanovk.core.platform.BaseViewModel
import ru.cherepanovk.core_preferences_api.data.PreferencesApi
import ru.cherepanovk.feature_alarm_api.data.AlarmApi
import ru.cherepanovk.feature_alarm_api.data.NotificationChannelCreator
import ru.cherepanovk.feature_google_calendar_api.data.GoogleCalendarApi
import javax.inject.Inject


class SettingViewModel @Inject constructor(
    private val getGoogleAccount: GetGoogleAccount,
    private val googleCalendarApi: GoogleCalendarApi,
    private val preferencesApi: PreferencesApi,
    private val notificationChannelCreator: NotificationChannelCreator
) : BaseViewModel() {
    private val _googleAccount = MutableLiveData<String>()
    val googleAccount: LiveData<String>
        get() = _googleAccount

    val logoutVisible = Transformations.map(googleAccount) { it.isNotEmpty() }

    private val _allIncomingChecked = MutableLiveData<Boolean>()
    val allIncomingChecked: LiveData<Boolean>
        get() = _allIncomingChecked

    private val _missedIncomingChecked = MutableLiveData<Boolean>()
    val missedIncomingChecked: LiveData<Boolean>
        get() = _missedIncomingChecked

    private val _allOutgoingChecked = MutableLiveData<Boolean>()
    val allOutgoingChecked: LiveData<Boolean>
        get() = _allOutgoingChecked

    private val _missedIncomingEnabled = MediatorLiveData<Boolean>()
    val missedIncomingEnabled: LiveData<Boolean>
        get() = _missedIncomingEnabled

    private val _chosenRingtone = MutableLiveData<Event<Pair<String, String>>>()
    val chosenRingtone: LiveData<Event<Pair<String, String>>>
        get() = _chosenRingtone

    private val _ringtoneTitle = MutableLiveData<Uri>()
    val ringtoneTitle: LiveData<Uri>
        get() = _ringtoneTitle

    init {
        loadAccount()
        loadPreferences()
        observeAccountState()
        _missedIncomingEnabled.addSource(allIncomingChecked) { checked ->
            _missedIncomingEnabled.postValue(!checked)
        }
    }


    fun logout() {
        preferencesApi.setGoogleAccount(null)
        loadAccount()
    }

    fun setAllIncoming(checked: Boolean) {
        preferencesApi.setTrackingAllIncomingCalls(checked)
        _missedIncomingEnabled.postValue(!checked)
        if (checked)
            setMissedIncoming(checked)
    }


    fun setAllOutgoing(checked: Boolean) {
        preferencesApi.setTrackingAllOutgoingCalls(checked)
    }


    fun setMissedIncoming(checked: Boolean) {
        _missedIncomingChecked.postValue(checked)
        preferencesApi.setTrackingMissedIncomingCalls(checked)
    }

    fun onAddAccountClick() {
    }


    fun onRingtoneClick() {
        val ringtoneUri = preferencesApi.getRingToneUri()
        val channelId = notificationChannelCreator.getDefaultChannelId()
        _chosenRingtone.postValue(Event(Pair(ringtoneUri, channelId)))
    }

    fun setPickedRingtone(ringtone: Uri?) {
        ringtone?.let {
            preferencesApi.setRingToneUri(it.toString())
            _ringtoneTitle.postValue(ringtone)
        }
    }

    fun onResume() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationChannelCreator.getDefaultChannel()?.sound?.let {
                _ringtoneTitle.postValue(it)
            }
        }
    }


    private fun loadPreferences() {
        _missedIncomingChecked.postValue(preferencesApi.getTrackingMissedIncomingCalls())
        _allIncomingChecked.postValue(preferencesApi.getTrackingAllIncomingCalls())
        _allOutgoingChecked.postValue(preferencesApi.getTrackingAllOutgoingCalls())
    }

    private fun observeAccountState() {
        launch {
            googleCalendarApi.savingAccount().collect {
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