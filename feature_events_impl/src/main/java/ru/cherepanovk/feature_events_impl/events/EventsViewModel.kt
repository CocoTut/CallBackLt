package ru.cherepanovk.feature_events_impl.events

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import ru.cherepanovk.core.interactor.UseCase
import ru.cherepanovk.core.platform.BaseViewModel
import ru.cherepanovk.core.utils.Mapper
import ru.cherepanovk.core_db_api.data.Reminder
import ru.cherepanovk.feature_events_impl.event.NewReminder
import ru.cherepanovk.feature_events_impl.events.domain.GetAllEventsFromOldDb
import ru.cherepanovk.feature_events_impl.events.domain.GetRemindersFromDb
import ru.cherepanovk.feature_events_impl.events.domain.SaveRemindersToDb
import java.util.*
import javax.inject.Inject

class EventsViewModel @Inject constructor(
    private val getAllEventsFromOldDb: GetAllEventsFromOldDb,
    private val getRemindersFromDb: GetRemindersFromDb,
    private val saveRemindersToDb: SaveRemindersToDb
) : BaseViewModel() {

    val currentMonth = MutableLiveData<Int>()
    val itemsReminder = MutableLiveData<List<ItemReminder>>()
    val emptyListVisibility = MediatorLiveData<Boolean>()

    init {
        launchLoading {
            getAllEventsFromOldDb(UseCase.None()) {
                it.handleSuccess { reminders ->
                    saveRemindersFromOldBase(reminders)
                }
            }
        }
        currentMonth.postValue(getCurrentMonth())

        emptyListVisibility.addSource(itemsReminder){items ->
            emptyListVisibility.postValue(items.isEmpty())
        }
    }

    private fun getCurrentMonth(): Int {
        return Calendar.getInstance().get(Calendar.MONTH)
    }

    fun onMonthClick(month: Int) {
        currentMonth.postValue(month)
    }

    private fun saveRemindersFromOldBase(reminders: List<Reminder>) {
        val  itemReminderMapper = ItemReminderMapper()
        launchLoading {
            saveRemindersToDb(listOf(NewReminder()))
            getRemindersFromDb(UseCase.None()) {
                it.handleSuccess { reminders ->
                    itemsReminder.postValue(reminders.map { reminder ->
                        itemReminderMapper.map(
                            reminder
                        )
                    })
                }
            }
        }
    }
}