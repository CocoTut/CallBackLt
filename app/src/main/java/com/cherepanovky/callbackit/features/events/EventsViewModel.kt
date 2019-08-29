package com.cherepanovky.callbackit.features.events

import com.cherepanovky.callbackit.core.interactor.UseCase
import com.cherepanovky.callbackit.core.platform.BaseViewModel
import com.cherepanovky.callbackit.features.events.interactor.GetAllEventsFromOldDb
import com.cherepanovky.callbackit.features.events.interactor.GetRemindersFromDb
import com.cherepanovky.callbackit.features.events.interactor.SaveRemindersToDb
import ru.cherepanovk.core_db_api.data.Reminder
import javax.inject.Inject

class EventsViewModel @Inject constructor(
    private val getAllEventsFromOldDb: GetAllEventsFromOldDb,
    private val getRemindersFromDb: GetRemindersFromDb,
    private val saveRemindersToDb: SaveRemindersToDb
): BaseViewModel() {

    init {
        launchLoading {
            getAllEventsFromOldDb(UseCase.None()){it.handleSuccess {reminders ->
              saveRemindersFromOldBase(reminders)
            }}
        }

    }

    private fun saveRemindersFromOldBase(reminders: List<Reminder>){
        launchLoading {
            saveRemindersToDb(reminders)
            getRemindersFromDb(UseCase.None()){it.handleSuccess {
                it
            }}
        }
    }
}