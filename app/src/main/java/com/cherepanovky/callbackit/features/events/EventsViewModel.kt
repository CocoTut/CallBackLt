package com.cherepanovky.callbackit.features.events

import com.cherepanovky.callbackit.core.interactor.UseCase
import com.cherepanovky.callbackit.core.platform.BaseViewModel
import com.cherepanovky.callbackit.features.events.interactor.GetAllEventsFromOldDb
import javax.inject.Inject

class EventsViewModel @Inject constructor(
    private val getAllEventsFromOldDb: GetAllEventsFromOldDb
): BaseViewModel() {

    init {
        launchLoading {
            getAllEventsFromOldDb(UseCase.None()){it.handleSuccess {
                it
            }}
        }

    }
}