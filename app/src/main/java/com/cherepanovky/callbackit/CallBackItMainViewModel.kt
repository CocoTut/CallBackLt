package com.cherepanovky.callbackit

import ru.cherepanovk.core.platform.BaseViewModel
import ru.cherepanovk.core_domain_api.data.CallListenerHadler
import ru.cherepanovk.core_domain_api.data.NotificationChannelCreator
import javax.inject.Inject

class CallBackItMainViewModel @Inject constructor(
    private val notificationChannelCreator: NotificationChannelCreator,
    private val callListenerHandler: CallListenerHadler
) : BaseViewModel() {

    init {
        notificationChannelCreator.createDefaultNotificationChannel(ringtoneUri = null)
        notificationChannelCreator.createMuteNotificationChannel()
        callListenerHandler.startCallLister()
    }
}