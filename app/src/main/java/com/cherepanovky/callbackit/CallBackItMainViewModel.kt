package com.cherepanovky.callbackit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.cherepanovk.core.config.AppConfig
import ru.cherepanovk.core.platform.BaseViewModel
import ru.cherepanovk.core_preferences_api.data.PreferencesApi
import ru.cherepanovk.feature_alarm_api.data.NotificationChannelCreator
import javax.inject.Inject

class CallBackItMainViewModel @Inject constructor(
    notificationChannelCreator: NotificationChannelCreator,
    private val preferencesApi: PreferencesApi,
    private val appConfig: AppConfig
) : BaseViewModel() {
    private val _accountName = MutableLiveData<String>()
    val accountName: LiveData<String>
        get() = _accountName

    private val _appVersion = MutableLiveData<String>()
    val appVersion: LiveData<String>
        get() = _appVersion

    init {
        notificationChannelCreator.createDefaultNotificationChannel(ringtoneUri = null)
        notificationChannelCreator.createMuteNotificationChannel()
    }

    fun initAccountName() {
        _accountName.postValue(preferencesApi.getGoogleAccount())
        _appVersion.postValue(appConfig.appVersion)
    }
}