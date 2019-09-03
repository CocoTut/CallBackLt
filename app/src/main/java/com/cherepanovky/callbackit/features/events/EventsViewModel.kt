package com.cherepanovky.callbackit.features.events
import androidx.lifecycle.MutableLiveData
import com.cherepanovky.callbackit.features.events.interactor.GetAllEventsFromOldDb
import com.cherepanovky.callbackit.features.events.interactor.GetRemindersFromDb
import com.cherepanovky.callbackit.features.events.interactor.SaveRemindersToDb
import ru.cherepanovk.core.interactor.UseCase
import ru.cherepanovk.core.platform.BaseViewModel
import ru.cherepanovk.core_db_api.data.Reminder
import java.time.Month
import java.util.*
import javax.inject.Inject

class EventsViewModel @Inject constructor(
    private val getAllEventsFromOldDb: GetAllEventsFromOldDb,
    private val getRemindersFromDb: GetRemindersFromDb,
    private val saveRemindersToDb: SaveRemindersToDb
): BaseViewModel() {

    val currentMonth = MutableLiveData<Int>()

    init {
        launchLoading {
            getAllEventsFromOldDb(UseCase.None()){it.handleSuccess { reminders ->
              saveRemindersFromOldBase(reminders)
            }}
        }
        currentMonth.postValue(getCurrentMonth())
    }

    private fun getCurrentMonth(): Int {
      return Calendar.getInstance().get(Calendar.MONTH)
    }

    fun onMonthClick(month: Int) {
        currentMonth.postValue(month)
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