package ru.cherepanovk.feature_events_impl.events

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import ru.cherepanovk.core.interactor.UseCase
import ru.cherepanovk.core.platform.BaseViewModel
import ru.cherepanovk.core.utils.Mapper
import ru.cherepanovk.core_db_api.data.Reminder
import ru.cherepanovk.feature_events_impl.event.NewReminder
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

    val currentMonth = MutableLiveData<Int>()
    val currentYear = MutableLiveData<Int>()
    val itemsReminder = MediatorLiveData<List<ItemReminder>>()
    val emptyListVisibility = MediatorLiveData<Boolean>()
    val years = MutableLiveData<List<String>>()

    init {

        loadRemindersFromOldDb()

        loadYears()

        currentYear.postValue(getCurrentYear())

        currentMonth.postValue(getCurrentMonth())

        emptyListVisibility.addSource(itemsReminder) { items ->
            emptyListVisibility.postValue(items.isEmpty())
        }
    }


    fun onMonthClick(month: Int) {
        currentMonth.postValue(month)
        loadReminders(month, currentYear.value ?: getCurrentYear())
    }

    fun yearSelected(year: Int?) {
        currentYear.value = year ?: getCurrentYear()
        loadReminders(currentMonth.value ?: getCurrentMonth(), currentYear.value!!)
    }

    fun loadData() {
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
                    val items = reminders.map { reminder -> itemReminderMapper.map(reminder) }
                    itemsReminder.postValue(items)
                }
            }
        }
    }

    private fun loadYears() {
        launchLoading {
            getYearsFromDb(UseCase.None()) {
                it.handleSuccess { list ->
                    years.postValue(list)
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