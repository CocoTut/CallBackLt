package ru.cherepanovk.feature_events_impl.events

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.cherepanovk.core.acc.Event
import ru.cherepanovk.core.interactor.UseCase
import ru.cherepanovk.core.platform.BaseViewModel
import ru.cherepanovk.core.platform.SingleLiveEvent
import ru.cherepanovk.core.utils.DateTimeHelper
import ru.cherepanovk.core_db_api.data.Reminder
import ru.cherepanovk.core_preferences_api.data.PreferencesApi
import ru.cherepanovk.feature_events_impl.events.domain.*
import java.util.*
import javax.inject.Inject

class EventsViewModel @Inject constructor(
    private val getAllEventsFromOldDb: GetAllEventsFromOldDb,
    private val saveRemindersToDb: SaveRemindersToDb,
    private val getRemindersBetweenDates: GetRemindersFromDbBetweenDates,
    private val itemReminderMapper: ItemReminderMapper,
    private val getYearsFromDb: GetYearsFromDb,
    private val askGoogleAccount: AskGoogleAccount,
    private val dateHelper: DateTimeHelper,
    private val loadEventsOfCalendar: LoadEventsOfCalendar,
    private val preferencesApi: PreferencesApi,
    private val getGoogleAccountFromOldDb: GetGoogleAccountFromOldDb
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

    private val _askGoogleCalendarAccount = SingleLiveEvent<Boolean>()
    val askGoogleCalendarAccount: LiveData<Boolean>
        get() = _askGoogleCalendarAccount

    private val _createNewReminder = MutableLiveData<Event<String>>()
    val createNewReminder: LiveData<Event<String>>
    get() = _createNewReminder

    init {

        if (!preferencesApi.isOldBaseMigrated()) {
            loadRemindersFromOldDb()
            loadGoogleAccountFromOldDb()
        }

        loadData()

        _currentYear.postValue(dateHelper.getCurrentYear())

        _currentMonth.postValue(dateHelper.getCurrentMonth())

        _emptyListVisibility.addSource(itemsReminder) { items ->
            _emptyListVisibility.postValue(items.isEmpty())
        }
    }

    fun onBtnAddNewReminderClick() {
        _createNewReminder.postValue(Event(preferencesApi.getLastCalledPhoneNumber() ?: ""))
    }

    fun onMonthClick(month: Int) {
        _currentMonth.postValue(month)
        loadReminders(month, currentYear.value ?: dateHelper.getCurrentYear())
    }

    fun yearSelected(year: Int?) {
        _currentYear.value = year ?: dateHelper.getCurrentYear()
        loadReminders(currentMonth.value ?: dateHelper.getCurrentMonth(), currentYear.value!!)
    }

    fun checkGoogleAccount() {
        launchLoading {
            askGoogleAccount(UseCase.None()) {
                it.handleSuccess { needAsk ->
                    if (needAsk)
                        _askGoogleCalendarAccount.postValue(needAsk)
                }
            }
        }
    }


    fun updateReminders() {
        launchLoading {
            loadEventsOfCalendar(
                LoadEventsOfCalendar.Params(
                    dateHelper.getStartDate(
                        month = currentMonth.value ?: dateHelper.getCurrentMonth(),
                        year = dateHelper.getCurrentYear()
                    ),
                    dateHelper.getEndDate(
                        month = currentMonth.value ?: dateHelper.getCurrentMonth(),
                        year = dateHelper.getCurrentYear()
                    )
                )
            ) { it.handleOnlyFailure() }
        }
    }

    private fun loadGoogleAccountFromOldDb() {
        launch {
            getGoogleAccountFromOldDb(UseCase.None()){
                it.handleSuccess { account ->
                    account?.let {
                        preferencesApi.setGoogleAccount(account)
                    }
                }
            }
        }
    }


    private fun loadData() {
        loadReminders(
            currentMonth.value ?: dateHelper.getCurrentMonth(),
            currentYear.value ?: dateHelper.getCurrentYear()
        )

        loadYears()
    }

    private fun loadReminders(month: Int, year: Int) {
        val dates = GetRemindersFromDbBetweenDates.Params(
            dateHelper.getStartDate(month, year),
            dateHelper.getEndDate(month, year)
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
                    preferencesApi.setOldBaseMigrated(true)
                }
            }
        }
    }


    private fun saveRemindersFromOldBase(reminders: List<Reminder>) {
        launchLoading {
            saveRemindersToDb(reminders)
        }
    }

}