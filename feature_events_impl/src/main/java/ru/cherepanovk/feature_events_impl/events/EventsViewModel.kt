package ru.cherepanovk.feature_events_impl.events

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.cherepanovk.core.exception.Failure
import ru.cherepanovk.core.interactor.UseCase
import ru.cherepanovk.core.platform.BaseViewModel
import ru.cherepanovk.core_db_api.data.Reminder
import ru.cherepanovk.feature_events_impl.events.data.EventsRepository
import ru.cherepanovk.feature_events_impl.events.domain.*
import java.util.*
import javax.inject.Inject

class EventsViewModel @Inject constructor(
    private val getAllEventsFromOldDb: GetAllEventsFromOldDb,
    private val saveRemindersToDb: SaveRemindersToDb,
    private val getRemindersBetweenDates: GetRemindersFromDbBetweenDates,
    private val itemReminderMapper: ItemReminderMapper,
    private val getYearsFromDb: GetYearsFromDb
) : BaseViewModel() {

    private val _currentMonth = MutableLiveData<Int>()
    val currentMonth: LiveData<Int>
        get() = _currentMonth

    private val _currentYear = MutableLiveData<Int>()
    val currentYear: LiveData<Int>
        get() = _currentYear

    private val _itemsReminder = MediatorLiveData<List<ItemReminder>>()
    val itemsReminder: LiveData<List<ItemReminder>>
        get() = _itemsReminder

    private val _emptyListVisibility = MediatorLiveData<Boolean>()
    val emptyListVisibility: LiveData<Boolean>
        get() = _emptyListVisibility

    private val _years = MutableLiveData<List<String>>()
    val years: LiveData<List<String>>
        get() = _years

    init {

//        loadRemindersFromOldDb()
        loadData()

        _currentYear.postValue(getCurrentYear())

        _currentMonth.postValue(getCurrentMonth())

        _emptyListVisibility.addSource(itemsReminder) { items ->
            _emptyListVisibility.postValue(items.isEmpty())
        }
    }


    fun onMonthClick(month: Int) {
        _currentMonth.postValue(month)
        loadReminders(month, currentYear.value ?: getCurrentYear())
    }

    fun yearSelected(year: Int?) {
        _currentYear.value = year ?: getCurrentYear()
        loadReminders(currentMonth.value ?: getCurrentMonth(), currentYear.value!!)
    }

    private fun loadData() {
        loadReminders(
            currentMonth.value ?: getCurrentMonth(),
            currentYear.value ?: getCurrentYear()
        )

        loadYears()
    }

    private fun loadReminders(month: Int, year: Int) {
        val dates = GetRemindersFromDbBetweenDates.Params(
            getStartDate(month, year),
            getEndDate(month, year)
        )
        launchLoading {
            getRemindersBetweenDates(dates) {
                it.handleSuccess { reminders ->
                    createItems(reminders)
                }
            }
        }

    }

    private fun createItems(reminders: Flow<List<Reminder>>) {
        launch {
            reminders.collect {
                val items = it.map { reminder -> itemReminderMapper.map(reminder) }
                _itemsReminder.postValue(items)
            }
        }
    }

    private fun loadYears() {
        launchLoading {
            getYearsFromDb(UseCase.None()) {
                it.handleSuccess { list ->
                    _years.postValue(list)
                }
            }
        }
    }

    private fun loadRemindersFromOldDb() {
        launchLoading {
            getAllEventsFromOldDb(UseCase.None()) {
                it.handleSuccess { reminders ->
                    saveRemindersFromOldBase(reminders)
                }
            }
        }
    }


    private fun getCurrentMonth(): Int {
        return Calendar.getInstance().get(Calendar.MONTH)
    }

    private fun getCurrentYear(): Int {
        return Calendar.getInstance().get(Calendar.YEAR)
    }

    private fun getStartDate(month: Int, year: Int): Date {
        val calendar = getCalendar(month, year)
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.clear(Calendar.MINUTE)
        calendar.clear(Calendar.SECOND)
        calendar.clear(Calendar.MILLISECOND)
        return calendar.time
    }

    private fun getEndDate(month: Int, year: Int): Date {
        val calendar = getCalendar(month, year)
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        return calendar.time
    }

    private fun getCalendar(month: Int, year: Int): Calendar {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.YEAR, year)
        return calendar
    }

    private fun saveRemindersFromOldBase(reminders: List<Reminder>) {
        launchLoading {
            saveRemindersToDb(reminders)
        }
    }
}