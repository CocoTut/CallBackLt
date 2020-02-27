package com.cherepanovky.callbackit

import android.content.Intent
import androidx.lifecycle.LiveData
import ru.cherepanovk.core.platform.BaseViewModel
import ru.cherepanovk.core.platform.SingleLiveEvent
import ru.cherepanovk.feature_google_calendar_api.data.GoogleCalendarApi
import ru.cherepanovk.feature_alarm_api.data.NotificationChannelCreator
import javax.inject.Inject

class CallBackItMainViewModel @Inject constructor(
    notificationChannelCreator: NotificationChannelCreator,
    api: GoogleCalendarApi
) : BaseViewModel() {

    private val _googleCalendarAccount = SingleLiveEvent<Intent>()
    val googleCalendarAccount: LiveData<Intent>
        get() = _googleCalendarAccount

    init {
        notificationChannelCreator.createDefaultNotificationChannel(ringtoneUri = null)
        notificationChannelCreator.createMuteNotificationChannel()
        _googleCalendarAccount.postValue(api.getChooseAccountIntent())
    }
}