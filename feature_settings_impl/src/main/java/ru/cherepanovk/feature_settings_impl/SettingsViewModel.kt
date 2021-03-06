package ru.cherepanovk.feature_settings_impl

import android.media.RingtoneManager
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.cherepanovk.core.acc.Event
import ru.cherepanovk.core.config.GooglePlayServicesAvailability
import ru.cherepanovk.core.interactor.UseCase
import ru.cherepanovk.core.platform.BaseViewModel
import ru.cherepanovk.core_preferences_api.data.PreferencesApi
import ru.cherepanovk.feature_alarm_api.data.NotificationChannelCreator
import ru.cherepanovk.feature_google_calendar_api.data.GoogleCalendarApi
import javax.inject.Inject


class SettingsViewModel @Inject constructor(
    private val getGoogleAccount: GetGoogleAccount,
    private val googleCalendarApi: GoogleCalendarApi,
    private val preferencesApi: PreferencesApi,
    private val notificationChannelCreator: NotificationChannelCreator,
    private val analyticsPlugin: SettingsAnalyticsPlugin,
    private val googlePlayServicesAvailability: GooglePlayServicesAvailability
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

    private val _whatsAppEnabled = MutableLiveData<Boolean>()
    val whatsAppEnabled: LiveData<Boolean>
        get() = _whatsAppEnabled

    private val _chosenRingtone = MutableLiveData<Event<Pair<String, String>>>()
    val chosenRingtone: LiveData<Event<Pair<String, String>>>
        get() = _chosenRingtone

    private val _ringtoneTitle = MutableLiveData<Uri>()
    val ringtoneTitle: LiveData<Uri>
        get() = _ringtoneTitle

    private val _durationAlarmTimes = MutableLiveData<Int>()
    val durationAlarmTimes: LiveData<Int>
        get() = _durationAlarmTimes

    private val _durationDelayAlarmMinutes = MutableLiveData<Int>()
    val durationDelayAlarmMinutes: LiveData<Int>
        get() = _durationDelayAlarmMinutes

    private val _repeatTimesAlarm = MutableLiveData<Int>()
    val repeatTimesAlarm: LiveData<Int>
        get() = _repeatTimesAlarm

    private val _longAlarmEnabled = MutableLiveData<Boolean>()
    val longAlarmEnabled: LiveData<Boolean>
        get() = _longAlarmEnabled

    val googleAccountAvailable: LiveData<Boolean> =
        MutableLiveData(googlePlayServicesAvailability.isGooglePlayServicesAvailable())

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

    fun setWhatsAppEnabled(checked: Boolean) {
        preferencesApi.setWhatsApp(checked)
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
            val ringtone = notificationChannelCreator.getDefaultChannel()?.sound
                ?: RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            _ringtoneTitle.postValue(ringtone)
            preferencesApi.setRingToneUri(ringtone.toString())
        }
    }

    fun onLongAlarmClick(checked: Boolean) {
        _longAlarmEnabled.postValue(checked)
        preferencesApi.setLongAlarmEnable(checked)
    }


    @FlowPreview
    fun setDurationAlarm(durationFlow: Flow<String>) {
        launch {
            durationFlow
                .distinctUntilChanged()
                .debounce(DEBOUNCE_DELAY)
                .collect {
                    if (it.isNotEmpty() && preferencesApi.getDurationAlarmTimes() != it.toInt()) {
                        preferencesApi.setDurationAlarmTimes(it.toInt())
                    }
                }
        }

    }

    @FlowPreview
    fun setDelayDurationAlarm(delay: Flow<String>) {
        launch {
            delay.debounce(DEBOUNCE_DELAY)
                .collect {
                    if (it.isNotEmpty() && preferencesApi.getDurationDelayAlarmMinutes() != it.toInt()) {
                        preferencesApi.setDurationDelayAlarmMinutes(it.toInt())
                    }
                }
        }
    }

    @FlowPreview
    fun setRepeatTimesAlarm(times: Flow<String>) {
        launch {
            times.debounce(DEBOUNCE_DELAY)
                .collect {
                    if (it.isNotEmpty() && preferencesApi.getRepeatAlarmTimes() != it.toInt()) {
                        preferencesApi.setRepeatAlarmTimes(it.toInt())
                    }
                }
        }
    }

    private fun loadPreferences() {
        _missedIncomingChecked.postValue(preferencesApi.getTrackingMissedIncomingCalls())
        _allIncomingChecked.postValue(preferencesApi.getTrackingAllIncomingCalls())
        _allOutgoingChecked.postValue(preferencesApi.getTrackingAllOutgoingCalls())
        _whatsAppEnabled.postValue(preferencesApi.getWhatsApp())
        _ringtoneTitle.postValue(Uri.parse(preferencesApi.getRingToneUri()))
        _durationAlarmTimes.postValue(preferencesApi.getDurationAlarmTimes())
        _durationDelayAlarmMinutes.postValue(preferencesApi.getDurationDelayAlarmMinutes())
        _longAlarmEnabled.postValue(preferencesApi.isLongAlarmEnable())
        _repeatTimesAlarm.postValue(preferencesApi.getRepeatAlarmTimes())
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

    companion object {
        private const val DEBOUNCE_DELAY = 1000L
    }
}