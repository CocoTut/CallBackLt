package com.cherepanovky.callbackit

import ru.cherepanovk.core.platform.BaseViewModel
import ru.cherepanovk.core_network_api.data.NetworkApi
import ru.cherepanovk.feature_alarm_api.data.NotificationChannelCreator
import javax.inject.Inject

class CallBackItMainViewModel @Inject constructor(
    notificationChannelCreator: NotificationChannelCreator,
    api: NetworkApi
) : BaseViewModel() {

    init {
        notificationChannelCreator.createDefaultNotificationChannel(ringtoneUri = null)
        notificationChannelCreator.createMuteNotificationChannel()
        launchLoading {
            api.calendarAuth()
        }
    }
}