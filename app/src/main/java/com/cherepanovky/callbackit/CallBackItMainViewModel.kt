package com.cherepanovky.callbackit

import ru.cherepanovk.core.platform.BaseViewModel
import ru.cherepanovk.feature_alarm_api.data.NotificationChannelCreator
import javax.inject.Inject

class CallBackItMainViewModel @Inject constructor(
    notificationChannelCreator: NotificationChannelCreator
) : BaseViewModel() {

    init {
        notificationChannelCreator.createDefaultNotificationChannel(ringtoneUri = null)
        notificationChannelCreator.createMuteNotificationChannel()
    }
}